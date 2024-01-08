package fr._14.pcd.codingweek15.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;


@Entity
@Setter
@Getter
@NoArgsConstructor
public final class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ColumnDefault("CURRENT_TIMESTAMP")
    private Date startDate;
    @ColumnDefault("CURRENT_TIMESTAMP")
    private Date endDate;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Element item;

    @ManyToOne
    @JoinColumn(name = "borrower_id")
    private User borrower;

    @OneToMany(mappedBy = "loan")
    @ColumnDefault("[]")
    private List<Message> messages;

    private Integer rating;

    public Loan(Date startDate, Date endDate, Element item, User borrower) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.item = item;
        this.borrower = borrower;
        this.rating = null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Loan loan = (Loan) o;
        System.out.println("id = " + id + ", startDate = " + startDate + ", endDate = " + endDate + ", item = " + item + ", borrower = " + borrower + ", rating = " + rating);
        System.out.println("loan.id = " + loan.id + ", loan.startDate = " + loan.startDate + ", loan.endDate = " + loan.endDate + ", loan.item = " + loan.item + ", loan.borrower = " + loan.borrower + ", loan.rating = " + loan.rating);
        return Objects.equals(id, loan.id) && Objects.equals(startDate, loan.startDate) && Objects.equals(endDate, loan.endDate) && Objects.equals(item, loan.item) && Objects.equals(borrower, loan.borrower) && Objects.equals(rating, loan.rating);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, startDate, endDate, item, borrower, rating);
    }
}
