package kr.co.lotteon.dto;

import lombok.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ResponseOptionDTO {
    public LinkedHashMap<String, List<ProdOptionDTO>> optionMap;
    public List<OptionDetailDTO> optionDetailDTOs;
}
