package project_wifi;

import java.util.List;

public class TbPublicWifiInfo {
    private int list_total_count;
    private List<WifiSpot> row;

    public int getList_total_count() {
        return list_total_count;
    }

    public void setList_total_count(int list_total_count) {
        this.list_total_count = list_total_count;
    }

    public List<WifiSpot> getRow() {
        return row;
    }

    public void setRow(List<WifiSpot> row) {
        this.row = row;
    }
}
