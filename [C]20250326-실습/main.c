#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "ociCRUD.h"

int main() {
    OCIContext ctx;
    char* username = "system";
    char* password = "1234";
    char* dbname = "localhost:1521/xe";

    // 1. 환경 초기화
    if (initOCIContext(&ctx) != 0) {
        printf("OCI 환경 초기화 실패\n");
        return -1;
    }

    // 2. 서버 연결 및 로그인
    if (OCIServerAttach(ctx.srvhp, ctx.errhp, (text*)dbname, strlen(dbname), OCI_DEFAULT) != OCI_SUCCESS) {
        printf("서버 연결 실패\n");
        freeOCIContext(&ctx);
        return -1;
    }
    OCIAttrSet(ctx.svchp, OCI_HTYPE_SVCCTX, ctx.srvhp, 0, OCI_ATTR_SERVER, ctx.errhp);

    OCIAttrSet(ctx.usrhp, OCI_HTYPE_SESSION, (void*)username, strlen(username), OCI_ATTR_USERNAME, ctx.errhp);
    OCIAttrSet(ctx.usrhp, OCI_HTYPE_SESSION, (void*)password, strlen(password), OCI_ATTR_PASSWORD, ctx.errhp);

    if (OCISessionBegin(ctx.svchp, ctx.errhp, ctx.usrhp, OCI_CRED_RDBMS, OCI_DEFAULT) != OCI_SUCCESS) {
        printf("세션 시작 실패\n");
        freeOCIContext(&ctx);
        return -1;
    }
    OCIAttrSet(ctx.svchp, OCI_HTYPE_SVCCTX, ctx.usrhp, 0, OCI_ATTR_SESSION, ctx.errhp);

    printf("Oracle DB 연결 성공!\n");

    // 3. 메뉴 반복
    int choice;
    while (1) {
        printf("\n[1] 거래 추가\n[2] 전체 거래 목록 보기\n[3] 고객 거래 목록 보기\n[4] 거래 수정\n[5] 거래 삭제\n[6] 종료\n선택: ");
        scanf("%d", &choice);
        getchar();

        if (choice == 1) {
            ociInsert(&ctx);
        }
        else if (choice == 2) {
            ociSelectAll(&ctx);
        }
        else if (choice == 3) {
            ociSelectByName(&ctx);
        }
        else if (choice == 4) {
            ociUpdateById(&ctx);
        }
        else if (choice == 5) {
            ociDeleteById(&ctx);
        }
        else if (choice == 6) {
            saveToBinary(&ctx, "transactions.dat");
            saveToCSV(&ctx, "transactions.csv");

            break;
        }
        else {
            printf("잘못된 선택입니다.\n");
        }
    }

    // 4. 자원 해제 및 종료
    freeOCIContext(&ctx);
    printf("DB 연결 종료 및 프로그램 종료\n");

    return 0;
}
