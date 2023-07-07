package com.example.demo.service;

import java.awt.Color;
import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.demo.model.Student;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import jakarta.servlet.http.HttpServletResponse;

public class PDF_Exporter {
	
	private static Logger log=LoggerFactory.getLogger(PDF_Exporter.class);

	private List<Student> listStudents;

	public PDF_Exporter(List<Student> listStudents) {
		this.listStudents = listStudents;
	}
	

	private void writeTableHeader(PdfPTable table) {
		log.debug("Entered table header cell creation "+getClass().getName());
		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(Color.GRAY);
		cell.setPadding(5);
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

		Font font = FontFactory.getFont(FontFactory.HELVETICA);
		font.setColor(Color.WHITE);
		font.setSize(10);

		cell.setPhrase(new Phrase("Name", font));

		table.addCell(cell);

		cell.setPhrase(new Phrase("Email", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Register Number", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Aadhar Number", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Gender", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Address", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Mobile Number", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Date Of Birth", font));
		table.addCell(cell);
		
		log.debug("Table header cell created "+getClass().getName());

	}

	private void writeTableData(PdfPTable table) {
		log.debug("Entered table data cell creater "+getClass().getName());
		
		PdfPCell cell = new PdfPCell();
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		cell.setBorderColor(Color.BLACK);

		Font font = FontFactory.getFont(FontFactory.HELVETICA);
		font.setColor(Color.BLACK);
		font.setSize(8);

		for (Student st : listStudents) {

			cell.setPhrase(new Phrase(st.getFirstName() + " " + st.getLastName(), font));
			table.addCell(cell);

			cell.setPhrase(new Phrase(st.getEmail(), font));
			table.addCell(cell);

			cell.setPhrase(new Phrase(String.valueOf(st.getRegNo()), font));
			table.addCell(cell);

			cell.setPhrase(new Phrase(String.valueOf(st.getAadharNo()), font));
			table.addCell(cell);

			cell.setPhrase(new Phrase(st.getGender(), font));
			table.addCell(cell);

			cell.setPhrase(new Phrase(st.getAddress(), font));
			table.addCell(cell);

			cell.setPhrase(new Phrase(String.valueOf(st.getMobNo()), font));
			table.addCell(cell);

			cell.setPhrase(new Phrase(st.getDob(), font));
			table.addCell(cell);
			
			log.debug("table data cell created "+getClass().getName());
			

		}
	}

	public void export(HttpServletResponse response) throws DocumentException, IOException {
		Document document = new Document(PageSize.A4);
		PdfWriter.getInstance(document, response.getOutputStream());

		String imagePath = "E:\\Share_Path\\Image\\k2_logo.png";

		document.open();
		
		Image image = Image.getInstance(imagePath);
		image.setAlignment(Image.TOP);
		image.scaleAbsolute(100f, 100f);
		document.add(image);

		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		font.setSize(18);
		font.setColor(Color.BLUE);

		Paragraph p = new Paragraph("~ STUDENTS LIST ~", font);
		p.setAlignment(Paragraph.ALIGN_CENTER);
		p.setSpacingAfter(10);
		document.add(p);

		PdfPTable table = new PdfPTable(8);
		table.setWidthPercentage(100f);
		table.setWidths(new float[] { 7.0f, 4.5f, 3.0f, 4.0f, 3.0f, 5.0f, 3.5f, 3.0f });
		table.setSpacingBefore(10);
		table.setSpacingAfter(10);
		

		writeTableHeader(table);
		writeTableData(table);

		document.add(table);

		document.close();
		log.debug("PDF Created"+getClass().getName());

	}
}
