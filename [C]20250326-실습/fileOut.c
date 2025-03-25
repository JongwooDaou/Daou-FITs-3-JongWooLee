#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "fileOut.h"

void saveToBinary(OCIContext* ctx, const char* filename) {
    FILE* binFile = fopen(filename, "wb");
    if (!binFile) {
        perror("바이너리 파일 열기 실패");
        return;
    }

    OCIStmt* stmt;
    char* sql = "SELECT id, customer_name, stock_name, trade_type, quantity, price FROM stock_transaction";
    OCIHandleAlloc(ctx->envhp, (void**)&stmt, OCI_HTYPE_STMT, 0, NULL);
    OCIStmtPrepare(stmt, ctx->errhp, (OraText*)sql, strlen(sql), OCI_NTV_SYNTAX, OCI_DEFAULT);

    stockTransaction tx;

    OCIDefine* d1 = NULL, * d2 = NULL, * d3 = NULL, * d4 = NULL, * d5 = NULL, * d6 = NULL;
    OCIDefineByPos(stmt, &d1, ctx->errhp, 1, &tx.id, sizeof(tx.id), SQLT_INT, NULL, NULL, NULL, OCI_DEFAULT);
    OCIDefineByPos(stmt, &d2, ctx->errhp, 2, tx.customerName, sizeof(tx.customerName), SQLT_STR, NULL, NULL, NULL, OCI_DEFAULT);
    OCIDefineByPos(stmt, &d3, ctx->errhp, 3, tx.stockName, sizeof(tx.stockName), SQLT_STR, NULL, NULL, NULL, OCI_DEFAULT);
    OCIDefineByPos(stmt, &d4, ctx->errhp, 4, &tx.type, sizeof(tx.type), SQLT_AFC, NULL, NULL, NULL, OCI_DEFAULT);
    OCIDefineByPos(stmt, &d5, ctx->errhp, 5, &tx.quantity, sizeof(tx.quantity), SQLT_INT, NULL, NULL, NULL, OCI_DEFAULT);
    OCIDefineByPos(stmt, &d6, ctx->errhp, 6, &tx.price, sizeof(tx.price), SQLT_FLT, NULL, NULL, NULL, OCI_DEFAULT);

    OCIStmtExecute(ctx->svchp, stmt, ctx->errhp, 0, 0, NULL, NULL, OCI_DEFAULT);

    while (OCIStmtFetch2(stmt, ctx->errhp, 1, OCI_FETCH_NEXT, 0, OCI_DEFAULT) == OCI_SUCCESS) {
        fwrite(&tx, sizeof(stockTransaction), 1, binFile);
    }

    fclose(binFile);
    OCIHandleFree(stmt, OCI_HTYPE_STMT);
    printf(">> 바이너리 파일 저장 완료 (%s)\n", filename);
}

void saveToCSV(OCIContext* ctx, const char* filename) {
    FILE* csv = fopen(filename, "w");
    if (!csv) {
        perror("CSV 파일 열기 실패");
        return;
    }

    fprintf(csv, "id,customer,stock,type,quantity,price\n");

    OCIStmt* stmt;
    char* sql = "SELECT id, customer_name, stock_name, trade_type, quantity, price FROM stock_transaction";
    OCIHandleAlloc(ctx->envhp, (void**)&stmt, OCI_HTYPE_STMT, 0, NULL);
    OCIStmtPrepare(stmt, ctx->errhp, (OraText*)sql, strlen(sql), OCI_NTV_SYNTAX, OCI_DEFAULT);

    stockTransaction tx;

    OCIDefine* d1 = NULL, * d2 = NULL, * d3 = NULL, * d4 = NULL, * d5 = NULL, * d6 = NULL;
    OCIDefineByPos(stmt, &d1, ctx->errhp, 1, &tx.id, sizeof(tx.id), SQLT_INT, NULL, NULL, NULL, OCI_DEFAULT);
    OCIDefineByPos(stmt, &d2, ctx->errhp, 2, tx.customerName, sizeof(tx.customerName), SQLT_STR, NULL, NULL, NULL, OCI_DEFAULT);
    OCIDefineByPos(stmt, &d3, ctx->errhp, 3, tx.stockName, sizeof(tx.stockName), SQLT_STR, NULL, NULL, NULL, OCI_DEFAULT);
    OCIDefineByPos(stmt, &d4, ctx->errhp, 4, &tx.type, sizeof(tx.type), SQLT_AFC, NULL, NULL, NULL, OCI_DEFAULT);
    OCIDefineByPos(stmt, &d5, ctx->errhp, 5, &tx.quantity, sizeof(tx.quantity), SQLT_INT, NULL, NULL, NULL, OCI_DEFAULT);
    OCIDefineByPos(stmt, &d6, ctx->errhp, 6, &tx.price, sizeof(tx.price), SQLT_FLT, NULL, NULL, NULL, OCI_DEFAULT);

    OCIStmtExecute(ctx->svchp, stmt, ctx->errhp, 0, 0, NULL, NULL, OCI_DEFAULT);

    while (OCIStmtFetch2(stmt, ctx->errhp, 1, OCI_FETCH_NEXT, 0, OCI_DEFAULT) == OCI_SUCCESS) {
        fprintf(csv, "%d,%s,%s,%c,%d,%.2lf\n", tx.id, tx.customerName, tx.stockName, tx.type, tx.quantity, tx.price);
    }

    fclose(csv);
    OCIHandleFree(stmt, OCI_HTYPE_STMT);
    printf(">> CSV 파일 저장 완료 (%s)\n", filename);
}
