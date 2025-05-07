package com.apple.shop.item;

// JPA로 데이터를 입출력하는 3-step
// 1. repository 생성
// 2. 원하는 클래스에 repository 등록
// 3. repository 입출력 문법 사용

import org.hibernate.query.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.awt.print.Pageable;
import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    // Page<Item> findPageBy(Pageable pageable);
    List<Item> findAllByTitleContains(String title);

    @Query(value = "SELECT * FROM shop.item WHERE MATCH(title) AGAINST(?1)",  nativeQuery = true)
    List<Item> fullTextSearch(String text);
}
