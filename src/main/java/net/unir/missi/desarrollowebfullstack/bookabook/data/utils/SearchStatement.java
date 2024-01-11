package net.unir.missi.desarrollowebfullstack.bookabook.data.utils;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SearchStatement {

    private String key;
    private Object value;
    private SearchOperation operation;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public SearchOperation getOperation() {
        return operation;
    }

    public void setOperation(SearchOperation operation) {
        this.operation = operation;
    }
}
