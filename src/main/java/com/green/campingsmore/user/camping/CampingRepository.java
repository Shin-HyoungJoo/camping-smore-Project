package com.green.campingsmore.user.camping;

import com.green.campingsmore.entity.CampEntity;
import com.green.campingsmore.user.camping.model.CampingDetailList;
import com.green.campingsmore.user.camping.model.CampingList;
import com.green.campingsmore.user.camping.model.CampingMyList;
import com.green.campingsmore.user.camping.model.CampingRes;
import io.lettuce.core.dynamic.annotation.Param;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface CampingRepository extends JpaRepository<CampEntity,Long>{
    @Query("select new com.green.campingsmore.user.camping.model.CampingList(c.icamp,c.name, c.address, c.campPhone,c.mainPic,n.city,c.delyn)"+
            " from CampEntity c join c.nationwideEntity n " + " where n.inationwide = :inationwide and c.delyn = :delyn")
    List<CampingList> selCamping(@Param("inationwide") Long inationwide, @Param("delyn") Integer delyn);

    @Query("select new com.green.campingsmore.user.camping.model.CampingList(c.icamp,c.name, c.address, c.campPhone,c.mainPic,n.city,c.delyn)"+
            " from CampEntity c join c.nationwideEntity n " + " where c.delyn = :delyn")
    List<CampingList> selCampingAll(@Param("delyn") Integer delyn);

    @Query("select new com.green.campingsmore.user.camping.model.CampingDetailList(c.icamp,c.name, c.campPhone, c.address,c.price,c.capacity,c.quantity,c.note,b.pic)"+
            " from CampEntity c join CampPicEntity b on b.campEntity.icamp = c.icamp" + " where c.icamp = :icamp")
    List<CampingDetailList> selDeCamping(@Param("icamp") Long icamp);

    @Query("select new com.green.campingsmore.user.camping.model.CampingMyList(b.ireserve, a.iuser, c.name, c.mainPic, c.campPhone)"+
            " from ReserveEntity b join b.userEntity a join b.campEntity c " + " where a.iuser = :iuser")
    List<CampingMyList> selMyList(@Param("iuser") Long iuser);

    @Query("select new com.green.campingsmore.user.camping.model.CampingList(c.icamp,c.name, c.address, c.campPhone,c.mainPic,n.city,c.delyn)"+
            " from CampEntity c join c.nationwideEntity n " + " where c.name LIKE CONCAT('%', :name, '%') and c.delyn = :delyn")
    List<CampingList> selTitleCamping(@Param("name") String name, @Param("delyn") Integer delyn);
}
