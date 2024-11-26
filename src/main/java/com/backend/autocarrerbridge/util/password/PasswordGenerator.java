package com.backend.autocarrerbridge.util.password;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.backend.autocarrerbridge.exception.AppException;

import static com.backend.autocarrerbridge.exception.ErrorCode.ERROR_INVALID_LENGTH_PASSWORD;
import static com.backend.autocarrerbridge.exception.ErrorCode.ERROR_MIN_LENGTH_PASSWORD;

public class PasswordGenerator {

    private final List<PasswordCharacterSet> pwSets; // Danh sách các tập ký tự sử dụng cho mật khẩu
    private final char[] allCharacters; // Mảng chứa tất cả các ký tự có thể dùng trong mật khẩu
    private final int minLength; // Chiều dài tối thiểu của mật khẩu
    private final int maxLength; // Chiều dài tối đa của mật khẩu
    private final int presetCharacterCount; // Số ký tự đã được phân bổ từ các tập ký tự đã định sẵn

    /**
     * Constructor để khởi tạo PasswordGenerator với minLength và maxLength
     *
     * @param minLength Chiều dài tối thiểu của mật khẩu, phải lớn hơn hoặc bằng 0.
     * @param maxLength Chiều dài tối đa của mật khẩu, phải lớn hơn hoặc bằng minLength.
     */
    public PasswordGenerator(int minLength, int maxLength) {
        // Kiểm tra tính hợp lệ của minLength và maxLength
        if (minLength < 0 || maxLength < minLength) {
            throw new AppException(ERROR_INVALID_LENGTH_PASSWORD);
        }
        this.minLength = minLength;
        this.maxLength = maxLength;

        // Khởi tạo danh sách các tập ký tự sử dụng để tạo mật khẩu
        List<PasswordCharacterSet> origPwSets = Arrays.asList(
                SummerCharacterSets.ALPHA_UPPER,  // Ký tự chữ hoa
                SummerCharacterSets.ALPHA_LOWER,  // Ký tự chữ thường
                SummerCharacterSets.NUMERIC,      // Ký tự số
                SummerCharacterSets.SPECIAL);     // Ký tự đặc biệt

        // Danh sách chứa các tập ký tự sau khi thêm các tập ký tự gốc
        List<PasswordCharacterSet> pwSets = new ArrayList<>();
        int pwCharacters = 0; // Tổng số ký tự từ tất cả các tập ký tự
        int preallocatedCharacters = 0; // Số ký tự được phân bổ tối thiểu từ các tập ký tự

        // Lặp qua các tập ký tự gốc và tính toán tổng số ký tự và ký tự phân bổ
        for (PasswordCharacterSet origPwSet : origPwSets) {
            pwSets.add(origPwSet);  // Thêm tập ký tự vào danh sách
            pwCharacters += origPwSet.getCharacters().length;  // Cộng số lượng ký tự từ tập ký tự
            preallocatedCharacters += origPwSet.getMinCharacters(); // Cộng số lượng ký tự tối thiểu từ tập ký tự
        }

        this.presetCharacterCount = preallocatedCharacters;  // Lưu số ký tự đã phân bổ
        this.pwSets = Collections.unmodifiableList(pwSets);  // Đảm bảo danh sách không thay đổi sau khi khởi tạo

        // Kiểm tra xem chiều dài mật khẩu tối thiểu có đủ lớn so với số ký tự đã phân bổ không
        if (minLength < presetCharacterCount) {
            throw new AppException(ERROR_MIN_LENGTH_PASSWORD);
        }

        // Tạo mảng chứa tất cả các ký tự có thể sử dụng trong mật khẩu
        char[] allChars = new char[pwCharacters];
        int currentIndex = 0;
        // Thêm các ký tự từ các tập ký tự vào mảng allChars
        for (PasswordCharacterSet pwSet : pwSets) {
            char[] chars = pwSet.getCharacters();
            System.arraycopy(chars, 0, allChars, currentIndex, chars.length);  // Sao chép ký tự vào mảng allChars
            currentIndex += chars.length;
        }
        this.allCharacters = allChars;  // Lưu mảng chứa tất cả các ký tự
    }

    /**
     * Phương thức tạo mật khẩu ngẫu nhiên
     * @return - Trả về mật khẩu dưới dạng chuỗi
     */
    public String generatePassword() {
        // Sử dụng SecureRandom để tạo số ngẫu nhiên an toàn hơn
        SecureRandom rand = new SecureRandom();
        // Chọn chiều dài mật khẩu ngẫu nhiên trong khoảng minLength và maxLength
        int pwLength = minLength + rand.nextInt(maxLength - minLength + 1);
        // Số ký tự còn lại cần được chọn ngẫu nhiên từ tất cả các ký tự
        int randomCharacterCount = pwLength - presetCharacterCount;

        // Danh sách các chỉ số còn lại để điền ký tự vào mật khẩu
        List<Integer> remainingIndexes = new ArrayList<>(pwLength);
        for (int i = 0; i < pwLength; ++i) {
            remainingIndexes.add(i);  // Thêm các chỉ số từ 0 đến pwLength-1 vào danh sách
        }

        char[] pw = new char[pwLength];  // Mảng chứa các ký tự của mật khẩu
        // Lặp qua các tập ký tự và thêm các ký tự vào mật khẩu với số lượng tối thiểu
        for (PasswordCharacterSet pwSet : pwSets) {
            addRandomCharacters(pw, pwSet.getCharacters(), pwSet.getMinCharacters(), remainingIndexes, rand);
        }
        // Thêm các ký tự ngẫu nhiên còn lại
        addRandomCharacters(pw, allCharacters, randomCharacterCount, remainingIndexes, rand);
        // Trả về mật khẩu dưới dạng chuỗi
        return new String(pw);
    }

    /**
     * Phương thức phụ để thêm ký tự ngẫu nhiên vào mật khẩu
     @param pw Mảng chứa mật khẩu đang được tạo, các ký tự sẽ được chèn vào mảng này.
      * @param characterSet Tập hợp các ký tự có thể được sử dụng để chèn vào mật khẩu.
     * @param numCharacters Số lượng ký tự cần được thêm vào mật khẩu từ tập ký tự cho trước.
     * @param remainingIndexes Danh sách các chỉ số còn lại trong mật khẩu mà ký tự có thể được chèn vào.
     * @param rand Đối tượng Random được sử dụng để tạo số ngẫu nhiên cho việc chọn chỉ số và ký tự.
     */
    private static void addRandomCharacters(
            char[] pw, char[] characterSet, int numCharacters, List<Integer> remainingIndexes, Random rand) {
        // Lặp để chọn và thêm các ký tự ngẫu nhiên vào mật khẩu
        for (int i = 0; i < numCharacters; ++i) {
            // Chọn ngẫu nhiên một chỉ số từ remainingIndexes
            int pwIndex = remainingIndexes.remove(rand.nextInt(remainingIndexes.size()));
            // Chọn ngẫu nhiên một ký tự từ characterSet
            int randCharIndex = rand.nextInt(characterSet.length);
            pw[pwIndex] = characterSet[randCharIndex];  // Gán ký tự vào mật khẩu tại vị trí pwIndex
        }
    }
}
