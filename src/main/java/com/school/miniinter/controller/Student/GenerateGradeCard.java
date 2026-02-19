package com.school.miniinter.controller.Student;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.school.miniinter.dao.GradeDAO;
import com.school.miniinter.dao.StudentsDAO;
import com.school.miniinter.models.Grades.GradeForSubject;
import com.school.miniinter.models.Students.BasicInfo;
import com.school.miniinter.models.Students.Students;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name="GenerateGradeCard", value={"/generateGradeCard"})
public class GenerateGradeCard extends HttpServlet {

    StudentsDAO studentsDAO = new StudentsDAO();
    GradeDAO gradeDAO = new GradeDAO();
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
            IOException{

        int id_student = Integer.parseInt(request.getParameter("idStudent"));

        Students student = studentsDAO.readById(id_student);
        BasicInfo basicInfo =
                studentsDAO.readBasicInfoStudent(student.getLogin());
        List<GradeForSubject> gradeForSubjects =
                gradeDAO.readAllGradesForStudent(id_student);

        generatePDF(response,basicInfo,gradeForSubjects);
    }

    public void generatePDF(HttpServletResponse response, BasicInfo student,
                            List<GradeForSubject> gradeForSubject) throws IOException{
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=boletim.pdf");

        PdfWriter writer = new PdfWriter(response.getOutputStream());
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        Paragraph title =
                new Paragraph("Boletim Escolar").setBold().setFontSize(18);
        title.setTextAlignment(TextAlignment.CENTER);
        document.add(title);

        document.add(new Paragraph("ALUNO(A): " + student.getFull_name()));
        document.add(new Paragraph("ANO LETIVO: " + student.getSchool_year()));
        document.add(new Paragraph("TURMA: " + student.getSeries()+"°"+student.getClassroom()));

//      table
        float[] colunas = {200f, 60f, 60f, 120f, 100f};
        Table table = new Table(colunas);
        table.setBorder(new SolidBorder(1));

        table.addHeaderCell("Subjects");
        table.addHeaderCell("n1");
        table.addHeaderCell("n2");
        table.addHeaderCell("FA");
        table.addHeaderCell("Situation");

        for (GradeForSubject g : gradeForSubject) {

            table.addCell(g.getSubject());
            table.addCell(String.valueOf(g.getN1()));
            table.addCell(String.valueOf(g.getN2()));
            table.addCell(String.valueOf(g.getAverage()));
            table.addCell(g.getSituation());
        }

        document.add(table);

        document.add(new Paragraph("\nFA - Final Average"));

        document.close();
    }
}
