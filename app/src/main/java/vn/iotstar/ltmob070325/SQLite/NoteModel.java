package vn.iotstar.ltmob070325.SQLite;

public class NoteModel {
    private int nid;
    private String content;

    public NoteModel() {}

    public NoteModel(int nid, String content) {
        this.nid = nid;
        this.content = content;
    }

    public int getNid() {
        return nid;
    }
    public void setNid(int nid) {
        this.nid = nid;
    }

    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
}

