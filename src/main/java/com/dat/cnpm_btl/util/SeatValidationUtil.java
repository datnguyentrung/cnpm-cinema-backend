package com.dat.cnpm_btl.util;

import com.dat.cnpm_btl.dto.catalog.SeatDTO;

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
        Map<Integer, List<Integer>> rowSelections = new HashMap<>();
        for (int[] seat : selectedSeats) {
            rowSelections.computeIfAbsent(seat[0], k -> new ArrayList<>()).add(seat[1]);
        }

        // 3. Logic Orphan Seat
        for (Map.Entry<Integer, List<Integer>> entry : rowSelections.entrySet()) {
            int rowIdx = entry.getKey();
            List<Integer> selectedCols = entry.getValue();
            int[] tempRow = Arrays.copyOf(seatMatrix[rowIdx], numCols);

            for (int col : selectedCols) tempRow[col] = 2;

            for (int c = 0; c < numCols; c++) {
                if (tempRow[c] == 1) {
                    int startC = c;
                    while (c + 1 < numCols && tempRow[c + 1] == 1) c++;
                    int blockLength = c - startC + 1;

                    if (blockLength == 1) {
                        int origStart = startC;
                        while (origStart > 0 && seatMatrix[rowIdx][origStart - 1] == 1) origStart--;

                        int origEnd = startC;
                        while (origEnd < numCols - 1 && seatMatrix[rowIdx][origEnd + 1] == 1) origEnd++;

                        int origBlockSize = origEnd - origStart + 1;

                        int selectedInBlock = 0;
                        for (int selectedCol : selectedCols) {
                            if (selectedCol >= origStart && selectedCol <= origEnd) selectedInBlock++;
                        }

                        if (selectedInBlock != origBlockSize - 1) {
                            return false; // Trả về false để hàm main ném Exception
                        }
                    }
                }
            }
        }
        return true;
    }
}
