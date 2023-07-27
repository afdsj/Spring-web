package upload;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class FileUploadController {

    @PostMapping("single-file")
    public String singFileUpload(@RequestParam MultipartFile singleFile, String singleFileDescription, Model model) {
        System.out.println("singleFile : " + singleFile);
        System.out.println("singleFileDescription : " + singleFileDescription);

        // 파일을 저장할 경로
        String root = "src/main/resources/static";
        // System.getProperty : C:\DEV\Spring-WebMvc\chap06-file-upload+ / + src/main/resources/static/uploadFiles
        String filePath = System.getProperty("user.dir")+"/"+root+"/uploadFiles";
        System.out.println(filePath);
        File dir = new File(filePath);

        if(!dir.exists()) {
            dir.mkdirs();
        }

        String originFileName = singleFile.getOriginalFilename();
        String ext = originFileName.substring(originFileName.lastIndexOf("."));
        String savedName = UUID/*랜덤한 난수를 발생*/.randomUUID().toString().replace("-", "") + ext;

        try {
            // C:\DEV\Spring-WebMvc\chap06-file-upload+ / + src/main/resources/static/uploadFiles/savedName.확장자
            singleFile.transferTo(new File(filePath+"/"+savedName));
            model.addAttribute("message", "파일 업로드 성공!");

        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("message", "파일 업로드 실패");
        }
        return "result";
    }

    @PostMapping("multi-file")
    public String multiFiles(@RequestParam List<MultipartFile>multiFiles, String multiFileDescription, Model model){
        System.out.println("multiFiles : " + multiFiles);
        System.out.println("multiFileDecription : " + multiFileDescription);

        // 파일을 저장할 경로
        String root = "src/main/resources/static";
        // System.getProperty : C:\DEV\Spring-WebMvc\chap06-file-upload+ / + src/main/resources/static/uploadFiles
        String filePath = System.getProperty("user.dir")+"/"+root+"/uploadFiles";
        System.out.println(filePath);

        File dir = new File(filePath);

        if(!dir.exists()){
            dir.mkdirs();
        }

        List<FileDTO> files = new ArrayList<>();
        try {
            for (MultipartFile file:multiFiles) {
                String originFileName = file.getOriginalFilename();
                String ext = originFileName.substring(originFileName.lastIndexOf("."));
                String savedName = UUID.randomUUID().toString().replace("-", "") + ext;

                // 저장된 값을 객체에 담음
                files.add(new FileDTO(originFileName, savedName, filePath, multiFileDescription));
                file.transferTo(new File(filePath+ "/"+savedName));
            }
            model.addAttribute("message","멀티 파일 저장됨");
        } catch (IOException e) {
            e.printStackTrace();
            for (FileDTO file: files) {
                new File(filePath+"/"+file.getSaveName()).delete();
            }
            model.addAttribute("message", "실패됨");
        }
        return "result";
    }
}

