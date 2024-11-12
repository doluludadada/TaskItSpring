// 在 backend/src/main/java/org/gu/taskmanager/controller 目錄下的 AuthController.java

package org.gu.taskmanager.controller;

import org.gu.taskmanager.entity.User;
import org.gu.taskmanager.repository.UserRepository;
import org.gu.taskmanager.service.CustomUserDetailsService;
import org.gu.taskmanager.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*; // 匯入 ResponseEntity 和 HttpStatus
import org.springframework.security.authentication.*; // 匯入 AuthenticationManager 和相關異常
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController // 表示這是一個 RESTful 控製器
@RequestMapping("/api/auth") // 設定請求路徑的前綴
public class AuthController {

    // 注入認證管理器
    @Autowired
    private AuthenticationManager authenticationManager;

    // 注入自訂的 UserDetailsService
    @Autowired
    private CustomUserDetailsService userDetailsService;

    // 注入使用者倉庫
    @Autowired
    private UserRepository userRepository;

    // 注入 JWT 工具類
    @Autowired
    private JwtUtil jwtUtil;

    // 注入密碼編碼器
    @Autowired
    private PasswordEncoder passwordEncoder;

    // 登錄介面
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            // 進行身份驗證
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            // 返回 401 未授權狀態碼
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        // 驗證通過後，載入使用者資訊
        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
        // 生成 JWT
        final String jwt = jwtUtil.generateToken(userDetails);

        // 返回包含 JWT 的響應
        return ResponseEntity.ok(new LoginResponse(jwt));
    }

    // 註冊介面
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        // 檢查使用者名稱是否已存在
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            return ResponseEntity.badRequest().body("Username is already taken");
        }
        // 檢查信箱是否已存在
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            return ResponseEntity.badRequest().body("Email is already registered");
        }

        // 建立新使用者
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        // 對密碼進行加密儲存
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        // 保存使用者到資料庫
        userRepository.save(user);

        // 返回成功響應
        return ResponseEntity.ok("User registered successfully");
    }

    // 登錄請求的 DTO 類
    public static class LoginRequest {
        private String username;
        private String password;
        // Getter 和 Setter 方法
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }

    // 登錄響應的 DTO 類
    public static class LoginResponse {
        private String jwt;
        public LoginResponse(String jwt) {
            this.jwt = jwt;
        }
        public String getJwt() {
            return jwt;
        }
    }

    // 註冊請求的 DTO 類
    public static class RegisterRequest {
        private String username;
        private String email;
        private String password;
        // Getter 和 Setter 方法
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }
}
