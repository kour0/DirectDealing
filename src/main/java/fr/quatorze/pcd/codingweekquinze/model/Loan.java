package fr.quatorze.pcd.codingweekquinze.model;

import fr.quatorze.pcd.codingweekquinze.model.element.Element;
import fr.quatorze.pcd.codingweekquinze.dao.LoanDAO;
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

    private Integer status = 0; // 0 = pending, 1 = accepted, 2 = canceled, 3 = ended
    private Integer rating;

    public Loan(Date startDate, Date endDate, Element item, User borrower) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.item = item;
        this.borrower = borrower;
        this.rating = null;
    }

    public void endLoan() {
        // TODO to be tested
        this.status = 3;
        LoanDAO.getInstance().update(this);
        this.borrower.pay(this.item.getPrice(), this.item.getOwner());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Loan loan = (Loan) o;
        return Objects.equals(id, loan.id) && Objects.equals(startDate, loan.startDate) && Objects.equals(endDate, loan.endDate) && Objects.equals(item, loan.item) && Objects.equals(borrower, loan.borrower) && Objects.equals(rating, loan.rating);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, startDate, endDate, item, borrower, rating);
    }

    public boolean isOverlapping(Date startDate, Date endDate) {
        if (this.startDate.equals(startDate) || this.endDate.equals(endDate)) {
            return true;
        }
        if (this.startDate.before(startDate) && this.endDate.after(startDate)) {
            return true;
        }
        if (this.startDate.before(endDate) && this.endDate.after(endDate)) {
            return true;
        }
        return this.startDate.after(startDate) && this.endDate.before(endDate);
    }
}