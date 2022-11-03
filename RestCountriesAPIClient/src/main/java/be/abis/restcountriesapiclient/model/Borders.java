package be.abis.restcountriesapiclient.model;

import java.util.ArrayList;
import java.util.List;

public class Borders {
    List<String> listCodes;

    public Borders() {
        this.listCodes = new ArrayList<>();
    }

    public List<String> getListCodes() {
        return listCodes;
    }

    public void setListCodes(List<String> listCodes) {
        this.listCodes = listCodes;
    }
}
