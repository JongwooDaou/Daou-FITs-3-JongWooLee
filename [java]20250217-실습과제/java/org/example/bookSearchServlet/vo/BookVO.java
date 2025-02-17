package org.example.bookSearchServlet.vo;

public class BookVO {
    private String bisbn;
    private String btitle;
    private int bprice;
    private String bauthor;

    public BookVO() {
    }

    public BookVO(String btitle, int bprice, String bauthor) {
        this.btitle = btitle;
        this.bprice = bprice;
    }

    public BookVO(String bisbn, String btitle, int bprice, String bauthor) {
        this.bisbn = bisbn;
        this.btitle = btitle;
        this.bprice = bprice;
        this.bauthor = bauthor;
    }

    public String getBisbn() {
        return bisbn;
    }

    public void setBisbn(String bisbn) {
        this.bisbn = bisbn;
    }

    public String getBtitle() {
        return btitle;
    }

    public void setBtitle(String btitle) {
        this.btitle = btitle;
    }

    public int getBprice() {
        return bprice;
    }

    public void setBprice(int bprice) {
        this.bprice = bprice;
    }

    public String getBauthor() {
        return bauthor;
    }

    public void setBauthor(String bauthor) {
        this.bauthor = bauthor;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("BookVO{");
        sb.append("bisbn='").append(bisbn).append('\'');
        sb.append(", btitle='").append(btitle).append('\'');
        sb.append(", bprice=").append(bprice);
        sb.append(", bauthor='").append(bauthor).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
