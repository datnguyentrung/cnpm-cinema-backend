package com.dat.cnpm_btl.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;

import java.security.SecureRandom;
import java.util.Objects;
import java.util.UUID;

public class TicketUtil {
    @Value("${jwt.base64-secret}")
    private static String jwtKey;

    // 1. Khởi tạo thuật toán sinh số an toàn (Thread-safe & Cryptographically secure)
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    // 2. BỘ TỪ ĐIỂN TỐI ƯU UX: Loại bỏ các ký tự dễ nhầm lẫn khi đọc bằng mắt thường (O và 0, I và 1, L)
    private static final char[] TICKET_CHAR_POOL = "ABCDEFGHJKMNPQRSTUVWXYZ23456789".toCharArray();

    // 3. Khai báo hằng số (Constants) để dễ bảo trì
    private static final int RANDOM_PART_LENGTH = 6;
    private static final String SEPARATOR = "-";

    // 4. Chặn khởi tạo object: Đã là Utility Class thì chỉ chứa static method, không được phép dùng lệnh 'new'
    private TicketUtil() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    // UTILITY - Generate unique ticket code
    public static String generateTicketCode(String prefix) {
        // 5. Validate Input (Fail-fast): Chặn ngay từ đầu nếu prefix bị null hoặc rỗng
        Objects.requireNonNull(prefix, "Ticket prefix must not be null");
        if (prefix.isBlank()) {
            throw new IllegalArgumentException("Ticket prefix must not be empty");
        }

        // 6. Dùng StringBuilder (tối ưu bộ nhớ hơn String concatenation cộng chuỗi)
        StringBuilder randomPartBuilder = new StringBuilder(RANDOM_PART_LENGTH);
        for (int i = 0; i < RANDOM_PART_LENGTH; i++) {
            int randomIndex = SECURE_RANDOM.nextInt(TICKET_CHAR_POOL.length);
            randomPartBuilder.append(TICKET_CHAR_POOL[randomIndex]);
        }
        String randomPart = randomPartBuilder.toString();

        // 7. Tính chữ ký điện tử (HMAC/Checksum)
        String signature = calculateSignature(randomPart);

        // 8. Dùng String.join thay vì String.format (String.join xử lý gộp chuỗi bằng ký tự phân cách nhanh hơn)
        return String.join(SEPARATOR, prefix.substring(0, 3).toUpperCase(), randomPart, signature);
    }

    private static String calculateSignature(String randomPart) {
        /// Trộn dữ liệu với "Muối" (Secret Key)
        String rawData = randomPart + jwtKey;

        // Băm một chiều bằng SHA-256
        String hash = DigestUtils.sha256Hex(rawData).toUpperCase();

        // Lấy 2 ký tự đầu tiên làm chữ ký cho ngắn gọn
        return hash.substring(0, 2);
    }

    // 3. Hàm kiểm tra vé giả ngay tại Gateway/Controller
    public static boolean validateTicketCode(String ticketCode) {
        if (ticketCode == null || ticketCode.isEmpty()) {
            return false;
        }

        try {
            String[] parts = ticketCode.split("-");
            if (parts.length != 3) return false;

            String randomPart = parts[1];
            String providedSignature = parts[2];

            // Server tự tính lại chữ ký dựa trên Secret Key của mình
            String expectedSignature = calculateSignature(randomPart);

            // So sánh
            return providedSignature.equals(expectedSignature);

        } catch (Exception e) {
            return false;
        }
    }
}
