package com.jongtix.book.springboot.utils;

import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor;
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;

//출처: https://woowabros.github.io/experience/2018/12/28/spring-rest-docs.html
//출처: https://huisam.tistory.com/entry/RESTDocs
public interface ApiDocumentUtils {

    static OperationRequestPreprocessor getDocumentRequest() {
        return preprocessRequest(
                modifyUris()    //문서상 uri를 기본값에서 원하는 값으로 변경하기 위해 사용
                    .scheme("http")
                    .host("ec2-52-78-213-172.ap-northeast-2.compute.amazonaws.com")
                    .removePort(),
                prettyPrint()   //문서의 request를 예쁘게 출력하기 위해 사용
        );
    }

    static OperationResponsePreprocessor getDocumentResponse() {
        return preprocessResponse(prettyPrint());
    }

}
