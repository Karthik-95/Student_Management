package com.example.demo.service;

import java.awt.Color;
import java.io.IOException;
import com.example.demo.model.Student;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfWriter;

import jakarta.servlet.http.HttpServletResponse;

public class PDF_ExporterIndivitual {

	private Student st;

	public PDF_ExporterIndivitual(Student st) {
		this.st = st;
	}

	public void export(HttpServletResponse response) throws DocumentException, IOException {
		Document document = new Document(PageSize.A4);
		document.setMargins(50, 50, 50, 50);
		PdfWriter writer= PdfWriter.getInstance(document, response.getOutputStream());

		document.open();
		
		String imagePath = "E:\\Share_Path\\Image\\k2_logo.png";
		
		Image image = Image.getInstance(imagePath);
		image.setAlignment(Image.TOP);
		image.scaleAbsolute(100f, 100f);
		document.add(image);
		
		//this below line add title (showe im browser) to the pdf file 
		//document.addTitle("STUDENT DETAILS");

		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		font.setSize(18);
		font.setColor(Color.BLUE);

		Font font1 = FontFactory.getFont(FontFactory.HELVETICA);
		font1.setSize(12);
		font1.setColor(Color.BLACK);

		Paragraph p = new Paragraph("-- "+st.getFirstName()+" Details --", font);
		p.setAlignment(Paragraph.ALIGN_CENTER);
		p.setSpacingAfter(20);
	
		document.add(p);
		
		PdfContentByte contend=writer.getDirectContent();
		//contend.setLineWidth(5);
		contend.setColorStroke(Color.GRAY);
		//System.out.println(document.getPageSize());
		
		contend.rectangle(50, 50, 500, 1);
		contend.rectangle(50, 650, 500, 1);
		//contend.rectangle(50,50,);
        contend.stroke();
		
		//contend.moveTo(50, 50);
		//contend.lineTo(540, 50);
		//the above is for line
		
		Paragraph p1=new Paragraph(" Aadhar No    :  " + st.getAadharNo(),font1);
		p1.setAlignment(Paragraph.ALIGN_LEFT);
		p1.setSpacingAfter(5);
		document.add(p1);
		
		Paragraph p2=new Paragraph(" First Name    :  " + st.getFirstName(),font1);
		p2.setAlignment(Paragraph.ALIGN_LEFT);
		p2.setSpacingAfter(5);
		document.add(p2);
		
		Paragraph p3=new Paragraph(" Last Name    :  " + st.getLastName(),font1);
		p3.setAlignment(Paragraph.ALIGN_LEFT);
		p3.setSpacingAfter(5);
		document.add(p3);
		
		Paragraph p4=new Paragraph(" Gender         :  " + st.getGender(),font1);
		p4.setAlignment(Paragraph.ALIGN_LEFT);
		p4.setSpacingAfter(5);
		document.add(p4);
		
		Paragraph p5=new Paragraph(" Email            :  " + st.getEmail(),font1);
		p5.setAlignment(Paragraph.ALIGN_LEFT);
		p5.setSpacingAfter(5);
		document.add(p5);
		
		Paragraph p6=new Paragraph(" Mobile No     :  " + st.getMobNo(),font1);
		p6.setAlignment(Paragraph.ALIGN_LEFT);
		p6.setSpacingAfter(5);
		document.add(p6);
		
		Paragraph p7=new Paragraph(" Address        :  " + st.getAddress(),font1);
		p7.setAlignment(Paragraph.ALIGN_LEFT);
		p7.setSpacingAfter(5);
		document.add(p7);
		
		Paragraph p8=new Paragraph(" DOB             :  " + st.getDob(),font1);
		p8.setAlignment(Paragraph.ALIGN_LEFT);
		p8.setSpacingAfter(5);
		document.add(p8);
	

		document.close();

	}

}
