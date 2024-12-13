//package com.nangman.hub.common.util;
//
//import com.nangman.hub.domain.entity.Hub;
//import com.nangman.hub.domain.repository.HubRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//import java.util.UUID;
//
//@RequiredArgsConstructor
//@Component
//public class DataRunner implements ApplicationRunner {
//
//    private final HubRepository hubRepository;
//
//    @Override
//    public void run(ApplicationArguments args) throws Exception {
//        List<Hub> hubList = List.of(
//                // TODO: managerId 실제 데이터로 바꾸기
//                new Hub("서울특별시 센터", "서울특별시 송파구 송파대로 55", 37.4742027808565, 127.123621185562, UUID.randomUUID()),
//                new Hub("경기 북부 센터", "경기도 고양시 덕양구 권율대로 570", 37.6403771056018, 126.87379545786, UUID.randomUUID()),
//                new Hub("경기 남부 센터", "경기도 이천시 덕평로 257-21", 37.1896213142136, 127.375050006958, UUID.randomUUID()),
//                new Hub("부산광역시 센터", "부산 동구 중앙대로 206", 35.117605126596, 129.045060216345, UUID.randomUUID()),
//                new Hub("대구광역시 센터", "대구 북구 태평로 161", 35.8758849492106, 128.596129208483, UUID.randomUUID()),
//                new Hub("인천광역시 센터", "인천 남동구 정각로 29", 37.4560499608337, 126.705255744089, UUID.randomUUID()),
//                new Hub("광주광역시 센터", "광주 서구 내방로 111", 35.1600994105234, 126.851461925213, UUID.randomUUID()),
//                new Hub("대전광역시 센터", "대전 서구 둔산로 100", 36.3503849976553, 127.384633005948, UUID.randomUUID()),
//                new Hub("울산광역시 센터", "울산 남구 중앙로 201", 35.5379472830778, 129.311256608093, UUID.randomUUID()),
//                new Hub("세종특별자치시 센터", "세종특별자치시 한누리대로 2130", 36.4800579897497, 127.289039408864, UUID.randomUUID()),
//                new Hub("강원특별자치도 센터", "강원특별자치도 춘천시 중앙로 1", 37.8800729197963, 127.727907820318, UUID.randomUUID()),
//                new Hub("충청북도 센터", "충북 청주시 상당구 상당로 82", 36.6353867908159, 127.491428436987, UUID.randomUUID()),
//                new Hub("충청남도 센터", "충남 홍성군 홍북읍 충남대로 21", 36.6590666265439, 126.672978750559, UUID.randomUUID()),
//                new Hub("전북특별자치도 센터", "전북특별자치도 전주시 완산구 효자로 225", 35.8194621650578, 127.106396942356, UUID.randomUUID()),
//                new Hub("전라남도 센터", "전남 무안군 삼향읍 오룡길 1", 34.8174988528003, 126.465423854957, UUID.randomUUID()),
//                new Hub("경상북도 센터", "경북 안동시 풍천면 도청대로 455", 36.5761205474728, 128.505722686385, UUID.randomUUID()),
//                new Hub("경상남도 센터", "경남 창원시 의창구 중앙대로 300", 35.2378032514675, 128.691940442146, UUID.randomUUID())
//        );
//        hubRepository.saveAll(hubList);
//    }
//}
