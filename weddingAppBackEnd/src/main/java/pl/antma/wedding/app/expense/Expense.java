package pl.antma.wedding.app.expense;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigInteger;

@Entity
public class Expense {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "type")
    private String type;

    @Column(name = "name")
    private String name;

    @Column(name = "min_cost")
    private BigInteger minCost;

    @Column(name = "max_cost")
    private BigInteger maxCost;

    @Column(name = "actual_cost")
    private BigInteger actualCost;

    @Column(name = "split_cost")
    private boolean splitCost;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigInteger getMinCost() {
        return minCost;
    }

    public void setMinCost(BigInteger minCost) {
        this.minCost = minCost;
    }

    public BigInteger getMaxCost() {
        return maxCost;
    }

    public void setMaxCost(BigInteger maxCost) {
        this.maxCost = maxCost;
    }

    public BigInteger getActualCost() {
        return actualCost;
    }

    public void setActualCost(BigInteger actualCost) {
        this.actualCost = actualCost;
    }

    public boolean isSplitCost() {
        return splitCost;
    }

    public void setSplitCost(boolean splitCost) {
        this.splitCost = splitCost;
    }
}
