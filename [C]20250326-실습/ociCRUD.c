#define _CRT_SECURE_NO_WARNINGS
#include "ociCRUD.h"
#include <stdio.h>
#include <string.h>
#include <oci.h>
#include <stdlib.h>

// OCI 환경 초기화
int initOCIContext(OCIContext* ctx) {
	OCIEnvCreate(&ctx->envhp, OCI_DEFAULT, NULL, NULL, NULL, NULL, 0, NULL);
	OCIHandleAlloc(ctx->envhp, (void**)&ctx->errhp, OCI_HTYPE_ERROR, 0, NULL);
	OCIHandleAlloc(ctx->envhp, (void**)&ctx->srvhp, OCI_HTYPE_SERVER, 0, NULL);
	OCIHandleAlloc(ctx->envhp, (void**)&ctx->svchp, OCI_HTYPE_SVCCTX, 0, NULL);
	OCIHandleAlloc(ctx->envhp, (void**)&ctx->usrhp, OCI_HTYPE_SESSION, 0, NULL);
	return 0;
}

// 리소스 해제
void freeOCIContext(OCIContext* ctx) {
	OCILogoff(ctx->svchp, ctx->errhp);
	OCIHandleFree(ctx->usrhp, OCI_HTYPE_SESSION);
	OCIHandleFree(ctx->svchp, OCI_HTYPE_SVCCTX);
	OCIHandleFree(ctx->srvhp, OCI_HTYPE_SERVER);
	OCIHandleFree(ctx->errhp, OCI_HTYPE_ERROR);
	OCIHandleFree(ctx->envhp, OCI_HTYPE_ENV);
}

void ociInsert(OCIContext* ctx) {
    stockTransaction* tx = (stockTransaction*)malloc(sizeof(stockTransaction));
    if (!tx) {
        printf("메모리 할당 실패\n");
        return;
    }

    // 입력 받기
    printf("고객 이름: ");
    scanf("%s", tx->customerName);

    printf("종목명: ");
    scanf("%s", tx->stockName);

    printf("거래유형 (B=매수, S=매도): ");
    scanf(" %c", &tx->type);

    printf("수량: ");
    scanf("%d", &tx->quantity);

    printf("가격: ");
    scanf("%lf", &tx->price);

    

    // SQL 작성 및 Statement 준비
    OCIStmt* stmt;
    char sql[256] = "INSERT INTO stock_transaction (customer_name, stock_name, trade_type, quantity, price) VALUES (:1, :2, :3, :4, :5)";
    OCIHandleAlloc(ctx->envhp, (void**)&stmt, OCI_HTYPE_STMT, 0, NULL);
    OCIStmtPrepare(stmt, ctx->errhp, (const OraText*)sql, strlen(sql), OCI_NTV_SYNTAX, OCI_DEFAULT);

    // 바인딩
    OCIBind* bnd1 = NULL;
    OCIBind* bnd2 = NULL;
    OCIBind* bnd3 = NULL;
    OCIBind* bnd4 = NULL;
    OCIBind* bnd5 = NULL;

    OCIBindByPos(stmt, &bnd1, ctx->errhp, 1, tx->customerName, sizeof(tx->customerName), SQLT_STR, NULL, NULL, NULL, 0, NULL, OCI_DEFAULT);
    OCIBindByPos(stmt, &bnd2, ctx->errhp, 2, tx->stockName, sizeof(tx->stockName), SQLT_STR, NULL, NULL, NULL, 0, NULL, OCI_DEFAULT);
    OCIBindByPos(stmt, &bnd3, ctx->errhp, 3, &tx->type, sizeof(tx->type), SQLT_AFC, NULL, NULL, NULL, 0, NULL, OCI_DEFAULT);
    OCIBindByPos(stmt, &bnd4, ctx->errhp, 4, &tx->quantity, sizeof(tx->quantity), SQLT_INT, NULL, NULL, NULL, 0, NULL, OCI_DEFAULT);
    OCIBindByPos(stmt, &bnd5, ctx->errhp, 5, &tx->price, sizeof(tx->price), SQLT_FLT, NULL, NULL, NULL, 0, NULL, OCI_DEFAULT);

    // SQL 실행
    OCIStmtExecute(ctx->svchp, stmt, ctx->errhp, 1, 0, NULL, NULL, OCI_DEFAULT);
    printf(">> 거래가 저장되었습니다.\n");

    // 정리
    OCIHandleFree(stmt, OCI_HTYPE_STMT);
    free(tx);
}
void ociSelectAll(OCIContext* ctx) {
    OCIStmt* stmt;
    char sql[128] = "SELECT id, customer_name, stock_name, trade_type, quantity, price FROM stock_transaction";
    OCIHandleAlloc(ctx->envhp, (void**)&stmt, OCI_HTYPE_STMT, 0, NULL);

    OCIStmtPrepare(stmt, ctx->errhp, (const OraText*)sql, (ub4)strlen(sql), OCI_NTV_SYNTAX, OCI_DEFAULT);

    // 구조체 동적 할당
    stockTransaction* tx = (stockTransaction*)malloc(sizeof(stockTransaction));
    if (!tx) {
        printf("메모리 할당 실패\n");
        return;
    }

    // Define
    OCIDefine* def1 = NULL, * def2 = NULL, * def3 = NULL, * def4 = NULL, * def5 = NULL, * def6 = NULL;

    OCIDefineByPos(stmt, &def1, ctx->errhp, 1, &tx->id, sizeof(tx->id), SQLT_INT, NULL, NULL, NULL, OCI_DEFAULT);
    OCIDefineByPos(stmt, &def2, ctx->errhp, 2, tx->customerName, sizeof(tx->customerName), SQLT_STR, NULL, NULL, NULL, OCI_DEFAULT);
    OCIDefineByPos(stmt, &def3, ctx->errhp, 3, tx->stockName, sizeof(tx->stockName), SQLT_STR, NULL, NULL, NULL, OCI_DEFAULT);
    OCIDefineByPos(stmt, &def4, ctx->errhp, 4, &tx->type, sizeof(tx->type), SQLT_AFC, NULL, NULL, NULL, OCI_DEFAULT);
    OCIDefineByPos(stmt, &def5, ctx->errhp, 5, &tx->quantity, sizeof(tx->quantity), SQLT_INT, NULL, NULL, NULL, OCI_DEFAULT);
    OCIDefineByPos(stmt, &def6, ctx->errhp, 6, &tx->price, sizeof(tx->price), SQLT_FLT, NULL, NULL, NULL, OCI_DEFAULT);

    OCIStmtExecute(ctx->svchp, stmt, ctx->errhp, 0, 0, NULL, NULL, OCI_DEFAULT);

    printf("\n[거래 내역]\n");
    printf("%-3s %-15s %-15s %-5s %-10s %-10s\n", "ID", "고객", "종목", "유형", "수량", "가격");

    while (OCIStmtFetch2(stmt, ctx->errhp, 1, OCI_FETCH_NEXT, 0, OCI_DEFAULT) == OCI_SUCCESS) {
        printf("%-3d %-15s %-15s %-5c %-10d %-10.2lf\n",
            tx->id, tx->customerName, tx->stockName, tx->type, tx->quantity, tx->price);
    }

    OCIHandleFree(stmt, OCI_HTYPE_STMT);
    free(tx);
}

void ociSelectByName(OCIContext* ctx) {
    char searchName[50];
    printf("검색할 고객 이름 입력: ");
    scanf("%s", searchName);

    OCIStmt* stmt;
    char sql[256] = "SELECT id, customer_name, stock_name, trade_type, quantity, price FROM stock_transaction WHERE customer_name = :1";
    OCIHandleAlloc(ctx->envhp, (void**)&stmt, OCI_HTYPE_STMT, 0, NULL);

    OCIStmtPrepare(stmt, ctx->errhp, (const OraText*)sql, (ub4)strlen(sql), OCI_NTV_SYNTAX, OCI_DEFAULT);

    // Bind 입력값
    OCIBind* bnd1 = NULL;
    OCIBindByPos(stmt, &bnd1, ctx->errhp, 1, searchName, sizeof(searchName), SQLT_STR, NULL, NULL, NULL, 0, NULL, OCI_DEFAULT);

    // 결과 저장할 구조체 동적할당
    stockTransaction* tx = (stockTransaction*)malloc(sizeof(stockTransaction));
    if (!tx) {
        printf("메모리 할당 실패\n");
        OCIHandleFree(stmt, OCI_HTYPE_STMT);
        return;
    }

    // Define
    OCIDefine* def1 = NULL, * def2 = NULL, * def3 = NULL, * def4 = NULL, * def5 = NULL, * def6 = NULL;
    OCIDefineByPos(stmt, &def1, ctx->errhp, 1, &tx->id, sizeof(tx->id), SQLT_INT, NULL, NULL, NULL, OCI_DEFAULT);
    OCIDefineByPos(stmt, &def2, ctx->errhp, 2, tx->customerName, sizeof(tx->customerName), SQLT_STR, NULL, NULL, NULL, OCI_DEFAULT);
    OCIDefineByPos(stmt, &def3, ctx->errhp, 3, tx->stockName, sizeof(tx->stockName), SQLT_STR, NULL, NULL, NULL, OCI_DEFAULT);
    OCIDefineByPos(stmt, &def4, ctx->errhp, 4, &tx->type, sizeof(tx->type), SQLT_AFC, NULL, NULL, NULL, OCI_DEFAULT);
    OCIDefineByPos(stmt, &def5, ctx->errhp, 5, &tx->quantity, sizeof(tx->quantity), SQLT_INT, NULL, NULL, NULL, OCI_DEFAULT);
    OCIDefineByPos(stmt, &def6, ctx->errhp, 6, &tx->price, sizeof(tx->price), SQLT_FLT, NULL, NULL, NULL, OCI_DEFAULT);

    // 실행
    OCIStmtExecute(ctx->svchp, stmt, ctx->errhp, 0, 0, NULL, NULL, OCI_DEFAULT);

    // 출력
    printf("\n[고객 '%s'의 거래 내역]\n", searchName);
    printf("%-3s %-15s %-15s %-5s %-10s %-10s\n", "ID", "고객", "종목", "유형", "수량", "가격");

    int found = 0;
    while (OCIStmtFetch2(stmt, ctx->errhp, 1, OCI_FETCH_NEXT, 0, OCI_DEFAULT) == OCI_SUCCESS) {
        printf("%-3d %-15s %-15s %-5c %-10d %-10.2lf\n",
            tx->id, tx->customerName, tx->stockName, tx->type, tx->quantity, tx->price);
        found = 1;
    }

    if (!found) {
        printf(">> 해당 고객의 거래 내역이 없습니다.\n");
    }

    // 정리
    OCIHandleFree(stmt, OCI_HTYPE_STMT);
    free(tx);
}

void ociUpdateById(OCIContext* ctx) {
    int id;
    double newPrice;

    printf("수정할 거래의 ID 입력: ");
    scanf("%d", &id);

    printf("새로운 가격 입력: ");
    scanf("%lf", &newPrice);

    OCIStmt* stmt;
    char sql[256] = "UPDATE stock_transaction SET price = :1 WHERE id = :2";
    OCIHandleAlloc(ctx->envhp, (void**)&stmt, OCI_HTYPE_STMT, 0, NULL);

    OCIStmtPrepare(stmt, ctx->errhp, (const OraText*)sql, strlen(sql), OCI_NTV_SYNTAX, OCI_DEFAULT);

    // Bind
    OCIBind* bnd1 = NULL;
    OCIBind* bnd2 = NULL;
    OCIBindByPos(stmt, &bnd1, ctx->errhp, 1, &newPrice, sizeof(newPrice), SQLT_FLT, NULL, NULL, NULL, 0, NULL, OCI_DEFAULT);
    OCIBindByPos(stmt, &bnd2, ctx->errhp, 2, &id, sizeof(id), SQLT_INT, NULL, NULL, NULL, 0, NULL, OCI_DEFAULT);

    // 실행
    OCIStmtExecute(ctx->svchp, stmt, ctx->errhp, 1, 0, NULL, NULL, OCI_DEFAULT);
    printf(">> 거래 ID %d 의 가격이 %.2lf로 수정되었습니다.\n", id, newPrice);

    OCIHandleFree(stmt, OCI_HTYPE_STMT);
}
void ociDeleteById(OCIContext* ctx) {
    int id;
    printf("삭제할 거래의 ID 입력: ");
    scanf("%d", &id);

    OCIStmt* stmt;
    char sql[128] = "DELETE FROM stock_transaction WHERE id = :1";
    OCIHandleAlloc(ctx->envhp, (void**)&stmt, OCI_HTYPE_STMT, 0, NULL);

    OCIStmtPrepare(stmt, ctx->errhp, (const OraText*)sql, strlen(sql), OCI_NTV_SYNTAX, OCI_DEFAULT);

    // Bind
    OCIBind* bnd1 = NULL;
    OCIBindByPos(stmt, &bnd1, ctx->errhp, 1, &id, sizeof(id), SQLT_INT, NULL, NULL, NULL, 0, NULL, OCI_DEFAULT);

    // 실행
    OCIStmtExecute(ctx->svchp, stmt, ctx->errhp, 1, 0, NULL, NULL, OCI_DEFAULT);
    printf(">> 거래 ID %d가 삭제되었습니다.\n", id);

    OCIHandleFree(stmt, OCI_HTYPE_STMT);
}





