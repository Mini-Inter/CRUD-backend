package com.school.miniinter.controller.Student;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.WebColors;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.properties.VerticalAlignment;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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

//        HEADER
        Paragraph title =
                new Paragraph("Boletim Escolar").setBold().setFontSize(18);

        ImageData imageData = ImageDataFactory.create(
                getServletContext().getRealPath("/img/logo.png")
        );

        Image logo = new Image(imageData);
        logo.setWidth(80);

        float[] colunasHeader = {1, 4};
        Table headerTable = new Table(colunasHeader);
        headerTable.useAllAvailableWidth();

        headerTable.addCell(new Cell().add(logo).setBorder(Border.NO_BORDER));

        headerTable.addCell(
                new Cell()
                        .add(title)
                        .setVerticalAlignment(VerticalAlignment.MIDDLE)
                        .setBorder(Border.NO_BORDER)
        );

        document.add(headerTable);

//        INFORMAÇÕES GERAIS
        float[] colunasInfo = {1, 1};
        Table infoTable = new Table(colunasInfo);
        infoTable.useAllAvailableWidth();

        infoTable.addCell(
                criarCelulaInfo("ALUNO(A): " + student.getFull_name(),
                        TextAlignment.LEFT)
        );

        infoTable.addCell(
                criarCelulaInfo("ANO LETIVO: " + student.getSchool_year(),
                    TextAlignment.RIGHT)
        );

        infoTable.addCell(
                criarCelulaInfo("TURMA: " +student.getSeries()+"° Série "+student.getClassroom() ,
                        TextAlignment.LEFT)
        );

        infoTable.addCell(
                criarCelulaInfo("UNIDADE: Escola Online - Vidya",
                        TextAlignment.RIGHT)
        );

        LocalDate data_atual = LocalDate.now();
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM" +
                "/yyyy");
        String dataAtual_formatada = data_atual.format(formatador);
        infoTable.addCell(
                criarCelulaInfo("EMISSÃO: "+dataAtual_formatada,
                        TextAlignment.LEFT)
        );

        String situacao_final = "Aprovado";
        for (GradeForSubject g: gradeForSubject){
            if(g.getSituation().equals("Reprovado")){
                situacao_final = "Reprovado";
                break;
            }
        }

        infoTable.addCell(
                new Cell()
                        .add(new Paragraph("SITUAÇÃO FINAL: "+situacao_final))
                        .setBorder(Border.NO_BORDER).setFontSize(8)
                        .setTextAlignment(TextAlignment.RIGHT)
                        .setPadding(5).setBold()
        );

        Color cinzaBorda = WebColors.getRGBColor("#CFCFCF");

        infoTable.setBorder(new SolidBorder(cinzaBorda, 1));
        infoTable.setMarginTop(25);

        document.add(infoTable);

//      table
        float[] colunas = {4, 1, 1, 1, 1.5f};
        Table table = new Table(colunas);
        table.useAllAvailableWidth();
        table.setBorder(new SolidBorder(ColorConstants.BLACK, 1));
        table.setMarginTop(25);

        table.addHeaderCell("Disciplinas");
        table.addHeaderCell("1°Sem").setTextAlignment(TextAlignment.CENTER);
        table.addHeaderCell("2°Sem").setTextAlignment(TextAlignment.CENTER);
        table.addHeaderCell("MF").setTextAlignment(TextAlignment.CENTER);
        table.addHeaderCell("Situação").setTextAlignment(TextAlignment.CENTER);

        for (GradeForSubject g : gradeForSubject) {

            table.addCell(celulaPadrao(g.getSubject(), TextAlignment.LEFT));

            table.addCell(celulaNota(g.getN1()));
            table.addCell(celulaNota(g.getN2()));
            table.addCell(celulaNota(g.getAverage()));

            table.addCell(celulaPadrao(g.getSituation(), TextAlignment.CENTER));
        }

        document.add(table);

        document.add(new Paragraph("\nMF - Média Final"));

        ImageData regraMedia = ImageDataFactory.create(
                getServletContext().getRealPath("/img/regraMedia.png")
        );

        Image img = new Image(regraMedia);
        img.setWidth(1000);
        img.setMarginTop(50);

        document.add(img);

        document.close();
    }

    private Cell criarCelulaInfo(String texto, TextAlignment alinhamento) {
        return new Cell()
                .add(new Paragraph(texto))
                .setFontSize(8)
                .setBorder(Border.NO_BORDER)
                .setTextAlignment(alinhamento)
                .setPadding(5);
    }

    private Cell celulaPadrao(String texto, TextAlignment alinhamento) {
        return new Cell()
                .add(new Paragraph(texto).setFontSize(9))
                .setTextAlignment(alinhamento)
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setBorder(new SolidBorder(ColorConstants.BLACK, 1))
                .setPadding(4);
    }

    Color cinzaHeader = WebColors.getRGBColor("#E6E6E6");

    private Cell celulaHeader(String texto) {
        return new Cell()
                .add(new Paragraph(texto)
                        .setBold()
                        .setFontSize(9))
                .setBackgroundColor(cinzaHeader)
                .setTextAlignment(TextAlignment.CENTER)
                .setBorder(new SolidBorder(ColorConstants.BLACK, 1))
                .setPadding(4);
    }

    private Cell celulaNota(Double nota) {

        Paragraph p;

        if (nota == null) {
            p = new Paragraph("-").setFontSize(9);
        } else {
            Text t = new Text(String.format("%.2f", nota))
                    .setFontSize(9);

            if (nota < 7) {
                t.setFontColor(ColorConstants.RED);
            }

            p = new Paragraph().add(t);
        }

        return new Cell()
                .add(p)
                .setTextAlignment(TextAlignment.CENTER)
                .setBorder(new SolidBorder(ColorConstants.BLACK, 1))
                .setPadding(4);
    }

}
