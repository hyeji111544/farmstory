package kr.co.lotteon.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MyOrderPageResponseDTO {

    private LinkedHashMap<Integer, List<OrderDetailDTO>> myOrderDTOList;

    private String cate;
    private String type;
    private String keyword;
    private LocalDate startDate;
    private LocalDate finalDate;


    private int pg;
    private int size;
    private int total;
    private int startNo;
    private int start, end;
    private boolean prev, next;

    @Builder
    public MyOrderPageResponseDTO(MyOrderPageRequestDTO pageRequestDTO, LinkedHashMap<Integer, List<OrderDetailDTO>> myOrderDTOList, int total){
        this.size = pageRequestDTO.getSize();
        this.cate = pageRequestDTO.getCate();
        this.type = pageRequestDTO.getType();
        this.startDate = pageRequestDTO.getStartDate();
        this.finalDate = pageRequestDTO.getFinalDate();

        this.pg = pageRequestDTO.getPg();
        this.keyword = pageRequestDTO.getKeyword();
        this.total = total;
        this.myOrderDTOList = myOrderDTOList;

        this.startNo = total - ((pg - 1) * size);
        this.end = (int) (Math.ceil(this.pg / 10.0)) * 10;
        this.start = this.end - 9;

        int last = (int) (Math.ceil(total / (double) size));
        this.end = end > last ? last : end;
        this.prev = this.start > 1;
        this.next = total > this.end * this.size;
    }

}
