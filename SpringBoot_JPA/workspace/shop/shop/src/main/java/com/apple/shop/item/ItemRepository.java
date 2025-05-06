package com.apple.shop.item;

// JPA로 데이터를 입출력하는 3-step
// 1. repository 생성
// 2. 원하는 클래스에 repository 등록
// 3. repository 입출력 문법 사용

import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {

}
