package uz.pdp.cache_simple_task2;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.TimeUnit;

@Slf4j
@SpringBootApplication
@EnableCaching
@EnableScheduling
public class FlywayApplication {
    public static void main(String[] args) {
        SpringApplication.run(FlywayApplication.class, args);
    }

    @CacheEvict(value = "students", allEntries = true)
    @Scheduled(initialDelay = 8, fixedDelay = 4 , timeUnit = TimeUnit.SECONDS)
    public void deleteAllCachedStudents() {
        log.info("All Entries Of Students Cache Flushing");
    }
}
// juda ko'p select qilinadigan malumotlarni keshlash kerak
// ko'p o'zgarmaydigan malumotlarda keshni ishlatish kerak
