package com.gerrymander.htmlObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.core.io.Resource;


@Configuration
public class PieChartPage {

//	@Bean
//	public boolean modifyPieHTML() {
//		try {
//            Path path = Paths.get("./src/main/resources/templates/demoPieFrag.html");
//            Stream <String> lines = Files.lines(path);
//            List <String> replaced = lines.map(line -> line.replaceAll("Humans", "male")).collect(Collectors.toList());
//            Files.write(path, replaced);
//            lines.close();
//
//            System.out.println("Find and Replace done!!!");
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//		return true;
//	}
	
//    @Value("classpath:./src/main/resources/static/templates/demoPieFrag.html")
//    private Resource sampleHtml;
//
//    @Bean
//    public String sampleHtmlData() throws IOException {
//        try(InputStream is = sampleHtml.getInputStream()) {
//            return IOUtils.toString(is);
//        }
//    }
}