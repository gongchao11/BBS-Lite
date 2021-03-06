/**
 * @classname
 * GitHub API DTO
 * @author bestsort
 * @date 19-8-22 下午7:55
 * @version 1.0
 */

package cn.bestsort.bbslite.dto;

import lombok.Data;


@Data
public class AccessTokenDto {
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
    private String state;

}
