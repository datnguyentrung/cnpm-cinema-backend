package com.dat.cnpm_btl.util;

import com.dat.cnpm_btl.dto.catalog.SeatDTO;

import java.util.*;

import java.util.*;

public class SeatValidationUtil {

    /**
     * Hàm chính để validate quy tắc chọn ghế
     */
    public static void validateSeatRules(
            List<SeatDTO.SeatResponse> allRoomSeats,
            List<SeatDTO.SeatResponse> selectedSeats,
            Set<Integer> bookedSeatIds) {

        // 1. Tự động tính toán kích thước ma trận (dựa trên tọa độ lớn nhất)
        int maxRow = allRoomSeats.stream().mapToInt(SeatDTO.SeatResponse::getGridRow).max().orElse(0) + 1;
        int maxCol = allRoomSeats.stream().mapToInt(SeatDTO.SeatResponse::getGridCol).max().orElse(0) + 1;

        int[][] seatMatrix = new int[maxRow][maxCol];

        // 2. Xây dựng ma trận với tốc độ O(N) nhờ dùng Set cho bookedSeatIds
        for (SeatDTO.SeatResponse seat : allRoomSeats) {
            int r = seat.getGridRow();
            int c = seat.getGridCol();

            // Nếu ghế không hoạt động hoặc ID nằm trong tập hợp ghế đã đặt -> Đánh số 0 (Không available)
            if (!seat.getIsActive() || bookedSeatIds.contains(seat.getSeatId())) {
                seatMatrix[r][c] = 0;
            } else {
                seatMatrix[r][c] = 1; // Available
            }
        }

        // 3. Chuyển đổi danh sách ghế được chọn thành mảng tọa độ
        // Ví dụ: Khách chọn ghế D3 (Hàng 3, Cột 3) và D4 (Hàng 3, Cột 4).
        // Kết quả selectedSeatsMatrix sẽ là: [[3, 3], [3, 4]].
        int[][] selectedSeatsMatrix = selectedSeats.stream()
                .map(s -> new int[]{s.getGridRow(), s.getGridCol()})
                .toArray(int[][]::new);

        // 4. Validate nghiệp vụ rạp phim
        if (!validateOrphanSeats(seatMatrix, selectedSeatsMatrix)) {
            // Ném Exception chuẩn thay vì in log
            throw new IllegalArgumentException("Vi phạm quy tắc: Không được để trống 1 ghế (Orphan Seat).");
        }
    }

    /**
     * Thuật toán lõi (giữ nguyên logic hoàn hảo của bạn)
     * Đầu vào: seatMatrix (ma trận ghế/bản đồ rạp phim), selectedSeats (danh sách ghế được chọn/tọa độ khách chọn)
     */
    private static boolean validateOrphanSeats(int[][] seatMatrix, int[][] selectedSeats) {
        int numRows = seatMatrix.length;
        int numCols = seatMatrix[0].length;

        // 1. Kiểm tra cơ bản
        for (int[] seat : selectedSeats) {
            int row = seat[0], col = seat[1];
            if (row < 0 || row >= numRows || col < 0 || col >= numCols || seatMatrix[row][col] != 1) {
                throw new IllegalArgumentException("Ghế được chọn không hợp lệ hoặc đã có người đặt.");
            }
        }

        // 2. Gom nhóm theo Hàng
        // Đầu vào selectedSeats: [[3, 3], [3, 4], [4, 5]] (Khách chọn 2 ghế hàng 3, 1 ghế hàng 4).
        // Kết quả rowSelections:
        //      Hàng 3 -> Danh sách cột: [3, 4]
        //      Hàng 4 -> Danh sách cột: [5]
        Map<Integer, List<Integer>> rowSelections = new HashMap<>();
        for (int[] seat : selectedSeats) {
            // Dùng computeIfAbsent để tối ưu hóa việc khởi tạo list
            // Nếu seat[0] đã tồn tại, nó sẽ trả về list hiện tại, nếu chưa, nó sẽ tạo mới một list rỗng
            // và trả về sau đó thêm seat[1] vào list đó
            rowSelections.computeIfAbsent(seat[0], k -> new ArrayList<>()).add(seat[1]);
        }

        // 3. Logic Orphan Seat (Kiểm tra ghế mồ côi)
        // VÍ DỤ: Hàng 0 có 7 ghế. Ghế 0 & 5 đã bán (0), còn lại trống (1).
        // Bản đồ gốc seatMatrix[0]: [0, 1, 1, 1, 1, 0, 1]
        // Khách hàng chọn mua ghế số 2 và 3 -> Vi phạm (vì để lại ghế số 1 lẻ loi)
        for (Map.Entry<Integer, List<Integer>> entry : rowSelections.entrySet()) {
            int rowIdx = entry.getKey();
            List<Integer> selectedCols = entry.getValue(); // Ví dụ: [2, 3]

            // BƯỚC 3.1: TẠO BẢN NHÁP VÀ ĐÁNH DẤU
            // Photocopy bản đồ gốc ra để đánh dấu nháp, không làm hỏng bản gốc
            int[] tempRow = Arrays.copyOf(seatMatrix[rowIdx], numCols);

            // Đánh dấu các ghế khách chọn bằng số 2
            // Bản nháp tempRow lúc này thành: [0, 1, 2, 2, 1, 0, 1]
            for (int col : selectedCols) tempRow[col] = 2;

            // BƯỚC 3.2: QUÉT TÌM GHẾ BỊ MỒ CÔI TRÊN BẢN NHÁP
            for (int c = 0; c < numCols; c++) {
                // Nếu thấy ghế mang số 1 (Nghĩa là ghế trống chưa ai chọn)
                if (tempRow[c] == 1) {
                    int startC = c;
                    // Chạy tới để đếm xem có bao nhiêu số 1 liền kề
                    while (c + 1 < numCols && tempRow[c + 1] == 1) c++;
                    int blockLength = c - startC + 1;

                    // Nếu blockLength == 1 -> Phát hiện 1 ghế mồ côi! (Ví dụ: quét trúng ghế số 1)
                    if (blockLength == 1) {

                        // BƯỚC 3.3: QUAY LẠI BẢN GỐC ĐỂ ĐIỀU TRA LAI LỊCH CỤM GHẾ NÀY
                        int origStart = startC;
                        // Lùi về trước trên seatMatrix gốc để tìm điểm bắt đầu của cụm (Ra được cột 1)
                        while (origStart > 0 && seatMatrix[rowIdx][origStart - 1] == 1) origStart--;

                        int origEnd = startC;
                        // Tiến về sau trên seatMatrix gốc để tìm điểm kết thúc của cụm (Ra được cột 4)
                        while (origEnd < numCols - 1 && seatMatrix[rowIdx][origEnd + 1] == 1) origEnd++;

                        // Cụm gốc ban đầu có mấy ghế? (Cột 4 - Cột 1 + 1 = 4 ghế)
                        int origBlockSize = origEnd - origStart + 1;

                        // BƯỚC 3.4: ĐẾM SỐ GHẾ KHÁCH ĐÃ "BỐC" KHỎI CỤM NÀY
                        int selectedInBlock = 0;
                        for (int selectedCol : selectedCols) { // Khách chọn [2, 3]
                            // Ghế 2 và 3 nằm trong khoảng [1..4] -> selectedInBlock = 2
                            if (selectedCol >= origStart && selectedCol <= origEnd) selectedInBlock++;
                        }

                        // BƯỚC 3.5: PHÁN QUYẾT CUỐI CÙNG
                        // Luật: Mua ghế chừa lại 1 ghế trống CHỈ ĐƯỢC PHÉP khi khách đã mua sạch cụm đó chỉ chừa đúng 1 cái.
                        // Nghĩa là: Số ghế khách mua (2) PHẢI BẰNG Tổng số ghế cụm gốc trừ đi 1 (4 - 1 = 3)
                        // Công thức: 2 != 3 -> ĐÚNG (Khách chọn sai) -> Trả về false báo lỗi!
                        if (selectedInBlock != origBlockSize - 1) {
                            return false; // Phạt thẻ đỏ! Trả về false để hàm main ném Exception
                        }
                    }
                }
            }
        }
        return true;
    }
}
