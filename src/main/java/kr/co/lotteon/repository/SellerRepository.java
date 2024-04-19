package kr.co.lotteon.repository;

import kr.co.lotteon.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SellerRepository extends JpaRepository<Seller, String> {
    public Optional<Seller> findBySellerName(String sellerName);
    public Optional<Seller> findByCompany(String company);
    public Optional<Seller> findByLicenseNum(String licenseNum );
    public Optional<Seller> findByBusinessNum(String businessNum);
    public Optional<Seller> findBySellerHp(String sellerHp);

}
