package com.jungsuk_2_1.postory.service;

import com.jungsuk_2_1.postory.controller.SubscriptionController;
import com.jungsuk_2_1.postory.dao.SubscriptionDao;
import com.jungsuk_2_1.postory.dto.SubscriptionChannelDto;
import com.jungsuk_2_1.postory.dto.SubscriptionPostDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class SubscriptionServiceImplTest {
    SubscriptionDao subscriptionDao;

    @Autowired
    SubscriptionServiceImplTest(SubscriptionDao subscriptionDao, SubscriptionServiceImpl subscriptionService) {
        this.subscriptionDao = subscriptionDao;
    }

    @Test
    void addToSubscriptionList() {
    }

    @Test
    void removeFromSubscriptionList() {
    }

    @Test
    void getSubscriptionPostList() {
        List<SubscriptionPostDto> list = subscriptionDao.selectSubscriptionPost("6a88ce41b5c24b29a56753a02c03aaaf");
        for (int i = 0; i < list.size(); i++) {
            System.out.println("list["+i+"]"+" = "+ list.get(i));
            System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------");
        }
        assertThat(!list.isEmpty()).isEqualTo(true);
        assertThat(list.size()).isEqualTo(8);
    }

    @Test
    void getSubscriptionChannelList() {
        List<SubscriptionChannelDto> list = subscriptionDao.selectSubscriptionChannel("6a88ce41b5c24b29a56753a02c03aaaf");
        for (int i = 0; i < list.size(); i++) {
            System.out.println("list["+i+"]"+" = "+ list.get(i));
            System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------");
        }
        assertThat(!list.isEmpty()).isEqualTo(true);
        assertThat(list.size()).isEqualTo(4);
    }
}