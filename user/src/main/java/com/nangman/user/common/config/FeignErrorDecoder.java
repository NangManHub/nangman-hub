package com.nangman.user.common.config;

import com.nangman.user.common.exception.CustomException;
import com.nangman.user.common.exception.ExceptionType;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
// FeignClient에서 에러 발생 시 호출되는 에러 처리 클래스
// 각 API 호출 시 반환되는 상태 코드에 따라 CustomException을 반환
public class FeignErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {

        log.info("methodKey : {}", methodKey); // 예시 methodKey : HubClient#getHub(UUID)
        log.info("reponse : {}", response); // 예시 reponse : HTTP/1.1 404

        // TODO 추후 에러 세분화
        switch (response.status()){
            case 404 :
                if (methodKey.contains("getHub")){
                    return new CustomException(ExceptionType.HUB_NOT_FOUND);
                }
                break;
            default:
                return new CustomException(ExceptionType.FEIGN_CLIENT_SERVER_ERROR);
        }
        return null;
    }
}
