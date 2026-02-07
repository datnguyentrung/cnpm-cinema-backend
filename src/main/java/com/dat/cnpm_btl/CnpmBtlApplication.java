package com.dat.cnpm_btl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.TimeZone;

@SpringBootApplication
@EnableJpaAuditing
public class CnpmBtlApplication {

    public static void main(String[] args) {
        // Thêm dòng này ở ngay đầu hàm main
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));

        SpringApplication.run(CnpmBtlApplication.class, args);
    }

}

