package com.youcode.citronix.entity.sales;

import com.youcode.citronix.entity.base.BaseEntity;
import com.youcode.citronix.entity.production.Harvest;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "sales")
public class Sale extends BaseEntity {
    
    @NotNull(message = "Sale date is required")
    @PastOrPresent(message = "Sale date cannot be in the future")
    @Column(nullable = false)
    private LocalDateTime saleDate;
    
    @NotNull(message = "Unit price is required")
    @Positive(message = "Unit price must be positive")
    @Column(nullable = false)
    private Double unitPrice;
    
    @NotNull(message = "Quantity is required")
    @Positive(message = "Quantity must be positive")
    @Column(nullable = false)
    private Double quantity;
    
    @NotBlank(message = "Client name is required")
    @Column(nullable = false)
    private String clientName;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "harvest_id", nullable = false)
    private Harvest harvest;
    
    @Column(nullable = false)
    private Double totalRevenue;
    
    @PrePersist
    @PreUpdate
    private void calculateRevenue() {
        this.totalRevenue = this.quantity * this.unitPrice;
    }
    
    @PrePersist
    @PreUpdate
    private void validateQuantity() {
        if (harvest != null && quantity > harvest.getTotalQuantity()) {
            throw new IllegalStateException("Sale quantity cannot exceed harvest quantity");
        }
    }
}
