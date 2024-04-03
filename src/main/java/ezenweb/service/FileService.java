package ezenweb.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Service
public class FileService {
    String uploadPath = "C:\\Users\\504\\Desktop\\ezen2023B_web2\\build\\resources\\main\\static\\uploadimg\\";


    public String fileUpload(MultipartFile multipartFile){
        //1. 파일 이름을 식별 가능한 uuid와 조합하기
        String uuid = UUID.randomUUID().toString();
        //2. 조합(uuid와 파일이름의 구분선이 _ 이기 때문에 파일명에 _가 존재할수도 있다 그러므로 _를 -로 치환)
        String filename = uuid+"_"+multipartFile.getOriginalFilename().replaceAll("_","-");

        File file = new File(uploadPath+filename);

        try{
            multipartFile.transferTo((file));
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return filename;
    }

}
