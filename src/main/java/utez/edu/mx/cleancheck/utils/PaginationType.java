package utez.edu.mx.cleancheck.utils;

import lombok.Data;

@Data

public class PaginationType {
    private String filter;
    private int page;
    private int limit;
    private String order;
    private String sortBy;

    @Override
    public String toString() {
        return "PaginationType{" +
                "filter='" + filter + '\'' +
                ", page=" + page +
                ", limit=" + limit +
                ", order='" + order + '\'' +
                ", sortBy='" + sortBy + '\'' +
                '}';
    }
}
