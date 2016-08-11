package com.example.dto;

import java.util.List;

/**
 * Created by Christian Sperandio on 31/07/2016.
 */
public class DataTablesDTO<T> {
    private final int draw;
    private final int recordsTotal;
    private final int recordsFiltered;
    private final List<T> data;
    private final String error;

    public DataTablesDTO(int draw, int recordsTotal, int recordsFiltered, List<T> data, String error) {
        this.draw = draw;
        this.recordsTotal = recordsTotal;
        this.recordsFiltered = recordsFiltered;
        this.data = data;
        this.error = error;
    }

    public int getDraw() {
        return draw;
    }

    public int getRecordsTotal() {
        return recordsTotal;
    }

    public int getRecordsFiltered() {
        return recordsFiltered;
    }

    public List<T> getData() {
        return data;
    }

    public String getError() {
        return error;
    }
}
