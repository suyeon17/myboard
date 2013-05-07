package com.myboard.dao;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

public class AssignmentFileParser {

	/*
	 * Method for writing Assignments or Assignment Submissions to XML file
	 * You shouldn't have to call this method directly, instead call the methods
	 * in the Assignments class or AssignmentSubmissions class
	 * Arguments:
	 * 		String name:	Full path + name of Assignment File name
	 * 		ArrayList<Question> questions: Assignment Questions
	 */
	protected static void writeToXML(String name, ArrayList<Question> questions) throws IOException{
		if(name == null || name.length() <= 0) throw new IOException();
		if(!name.contains(".xml")) name+=".xml";
		BufferedWriter out = new BufferedWriter(new FileWriter(name));
		out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		out.write("<assignment>\n");
		for(int i = 0; i < questions.size(); i++)
			questions.get(i).generateXML(out,i+1);
		out.write("</assignment>");
		out.flush();
		out.close();
	}
	
	/*
	 * Method for retrieving Assignments or Assignment Submissions from an XML file
	 * You shouldn't have to call this method directly, instead call the methods
	 * in the Assignments class or AssignmentSubmissions class
	 * Arguments:
	 * 		String name:	Full path + name of Assignment file name
	 * Returns:
	 * 		ArrayList<Question>: Returns the questions in the assignment or assignment submission
	 */
	protected static ArrayList<Question> getFromXML(String filename) throws IOException{
		try {
			return AssignmentParser.parseAssignmentXML(filename);
		} catch (ParserConfigurationException e) {
			throw new IOException("ParserConfigurationException: "+e.getLocalizedMessage());
		} catch (SAXException e) {
			throw new IOException("SAXException: "+e.getLocalizedMessage());
		}
	}

///////////////////////////////////////////////////////////////////
	/*
	 * Question Class	
	 */
	public static class Question {
		
		private String question;
		private Answer answer;

		public Question(){}
		
		public void setQuestion(String question){
			if(question == null) return;
			this.question = question;
		}
		
		public String getQuestion(){
			return this.question;
		}
		
		public void setAnswer(Answer a){
			if(a == null) return;
			this.answer = a;
		}
		
		public Answer getAnswer(){
			return this.answer;
		}
		
		/*
		 * Method for generating a multiple choice question
		 * Arguments:
		 * 		String question: The prompt to the question (e.g., What does this method do?)
		 *		String[] answers: The possible answers to the question
		 *						  (e.g., 0. Nothing
		 *								 1. Generates an object for you)
		 *		int correctAnswer: The correct answer to the question (in respect to the String[] answers argument)
		 *						  (e.g., 1 = Generates an object for you)	
		 *		int selectedAnswer: The answer for an assignment submission (-1 if none selected)
		 *	Returns:
		 *		Question object containing a MultipleChoiceAnswer
		 */
		public static Question generateMultipleChoiceQuestion(String question, String[] answers, int correctAnswer, int selectedAnswer){
			if(question == null || answers == null || answers.length < 1 || correctAnswer < 0 || correctAnswer >= answers.length)
				return null;
			Question q = new Question();
			q.setQuestion(question);
			MultipleChoiceAnswer a = new MultipleChoiceAnswer();
			for(int i = 0; i < answers.length; i++){
				if(i == selectedAnswer && i == correctAnswer){
					a.addCorrectAndSelectedAnswer(answers[i]);
				}else if(i == selectedAnswer){
					a.addSelectedAnswer(answers[i]);
				}else if(i == correctAnswer){
					a.addCorrectAnswer(answers[i]);
				}else a.addAnswer(answers[i]);
			}
			q.setAnswer(a);
			return q;
		}
		
		
		/*
		 * Method for generating a short answer question
		 * Arguments:
		 * 		String question: The prompt to the question (e.g., What does this method do?)
		 *		String response: The response to the question (may be null)
		 *						  (e.g., This method generates a short answer question for you.)
		 *	Returns:
		 *		Question object containing a ShortAnswer
		 */
		public static Question generateShortAnswerQuestion(String question, String response){
			if(question==null) return null;
			Question q = new Question();
			q.setQuestion(question);
			ShortAnswer answer = new ShortAnswer();
			if(response!=null)
				answer.setResponse(response);
			q.setAnswer(answer);
			return q;
		}
		
		
		/*
		 * Method for generating a file submission question
		 * Arguments:
		 * 		String question: The prompt to the question (e.g., What does this method do?)
		 *		String filename: The filename submitted (may be null)
		 *						  (e.g., HelloWorld.java)
		 *	Returns:
		 *		Question object containing a FileSubmissionAnswer
		 */
		public static Question generateFileSubmissionQuestion(String question, String filename){
			if(question == null) return null;
			Question q = new Question();
			q.setQuestion(question);
			FileSubmissionAnswer answer = new FileSubmissionAnswer();
			if(filename != null)
				answer.setFileName(filename);
			q.setAnswer(answer);
			return q;
		}
		
		/*
		 * Generates the XML for a question
		 * You should not have to call this method, since it is called by the writeToXML()
		 * method above
		 */
		protected void generateXML(BufferedWriter out, int questionNum) throws IOException{
			out.write("\t<question type=\"");
			if(this.answer instanceof MultipleChoiceAnswer){
				out.write("multiple_choice");
			}else if (this.answer instanceof FileSubmissionAnswer){
				out.write("file_submission");
			}else if(this.answer instanceof ShortAnswer){
				out.write("short_answer");
			}else throw new IOException("Unknown question type");
			out.write("\" number=\"");
			out.write(Integer.toString(questionNum));
			out.write("\">\n\t\t<prompt>");
			if(this.question != null && this.question.length()>0)
				out.write(this.question);
			out.write("</prompt>\n");
			this.answer.setNum(questionNum);
			this.answer.generateXML(out);
			out.write("\t</question>\n");
			out.flush();
		}//writeToXML
		
		
		/*
		 * Abstract answer class
		 */
		public static abstract class Answer {
			protected int num;
			protected abstract void generateXML(BufferedWriter out) throws IOException;
			protected void setNum(int num){ this.num = num;}
			protected int getNum(){return this.num;}
		}//Answer abstract class

		
		/*
		 * Answer class for Multiple Choice Questions
		 */
		public static class MultipleChoiceAnswer extends Answer{
			
			private ArrayList<String> answers;
			private int selectedAnswer;
			private int correctAnswer;
			
			public MultipleChoiceAnswer(){
				this.answers = new ArrayList<String>();
				this.selectedAnswer = -1;
				this.correctAnswer = -1;
			}
			
			public String getAnswer(int i){
				return (i < this.answers.size() || i >= 0) ? this.answers.get(i) : null;
			}
			
			public void addAnswer(String answer){
				if(answer == null) return;
				this.answers.add(answer);
			}
			
			public void addCorrectAnswer(String answer){
				if(answer == null) return;
				this.correctAnswer = this.answers.size();
				this.answers.add(answer);
			}
			
			public void addSelectedAnswer(String answer){
				if(answer == null) return;
				this.selectedAnswer = this.answers.size();
				this.answers.add(answer);
			}
			
			public void addCorrectAndSelectedAnswer(String answer){
				if(answer == null) return;
				this.correctAnswer = this.answers.size();
				this.selectedAnswer = this.correctAnswer;
				this.answers.add(answer);
			}
			
			public String getSelectedAnswer(){
				return getAnswer(this.selectedAnswer);
			}
			
			public void setSelectedAnswer(int i){
				if(i >= this.answers.size() || i < 0) return;
				this.selectedAnswer = i;
			}
			
			public String getCorrectAnswer(){
				return getAnswer(this.correctAnswer);
			}
			
			public void setCorrectAnswer(int i){
				if(i >= this.answers.size() && i < 0) return;
				this.correctAnswer = i;
			}
			
			@Override
			protected void generateXML(BufferedWriter out) throws IOException {
				for(int i = 0; i < this.answers.size(); i++){
					out.write("\t\t<choice");
					if(i==this.correctAnswer)
						out.write(" correct=\"true\"");
					if(i==this.selectedAnswer)
						out.write(" selected=\"true\"");
					out.write(">");
					out.write(this.answers.get(i));
					out.write("</choice>\n");
					out.flush();
				}
			}
			
			@Override
			public String toString(){
				StringBuilder b = new StringBuilder();
				for(int i = 0; i < this.answers.size(); i++){
					b.append(this.answers.get(i));
					if(i == this.correctAnswer)
						b.append(" {correct}");
					if(i == this.selectedAnswer)
						b.append("{selected}");
					if(i < this.answers.size()-1)
						b.append("\n");
				}
				return b.toString();
			}
		}//MultipleChoiceAnswer

		/*
		 * Answer class for File Submission Questions
		 */
		public static class FileSubmissionAnswer extends Answer{
			
			private String filename;
			
			public FileSubmissionAnswer(){
				super.setNum(-1);
			}
			
			public String getFileName(){
				return this.filename;
			}
			
			public void setFileName(String filename){
				if(filename == null) return;
				this.filename = filename;
			}
			
			/*
			 * Method called for writing the actual file
			 * Arguments:
			 * 		InputStream in: Inputstream from bean for reading file
			 * 		String submissionPath: Path returned by AssignmentSubmission.getAbsolutePath()
			 * 		int submissionId: Assignment submission Id
			 * Returns: True if file written successfully, false if file not written successfully
			 */
			public boolean writeFile(InputStream in, String submissionPath, int submissionId){
				if(submissionPath == null || submissionId < 0|| in == null || super.getNum() <= 0) return false;
				File f = new File(submissionPath+submissionId+"_"+super.getNum());
				if(f.exists()) return false;
				try {
					DataOutputStream out = new DataOutputStream(new FileOutputStream(f));
					int b = in.read();
					while(b >= 0){
						out.write(b);
						b = in.read();
					}
					out.close();
					return true;
				} catch (FileNotFoundException e) {
				} catch (IOException e) {}
				return false;
			}
			
			/*
			 * Method called for retrieving file object to write to Outputstream
			 * Arguments:
			 * 		String submissionPath: Path returned by AssignmentSubmission.getAbsolutePath()
			 * 		int submissionId: Assignment submission Id
			 * Returns: True if file written successfully, false if file not written successfully
			 */
			public File getFile(String submissionPath, int submissionId){
				if(submissionPath == null || submissionId < 0 || super.getNum() <= 0) return null;
				File f = new File(submissionPath+submissionId+"_"+super.getNum());
				if(!f.exists()) return null;
				return f;
			}
			
			@Override
			protected void generateXML(BufferedWriter out) throws IOException {
				if(this.filename == null || this.filename.length() <= 0) return;
				out.write("\t\t<file>");
				out.write(this.filename);
				out.write("</file>\n");
				out.flush();
			}
			
			@Override
			public String toString(){
				return this.filename;
			}
		}//FileSubmissionAnswer

		/*
		 * Answer class for Short Answers Questions
		 */
		public static class ShortAnswer extends Answer{
			
			private String response;
			
			public ShortAnswer(){}
			
			public void setResponse(String response){
				if(response == null) return;
				this.response = response;
			}
			
			public String getResponse(){
				return this.response;
			}

			@Override
			protected void generateXML(BufferedWriter out) throws IOException{
				if(this.response == null || this.response.length() <= 0) return;
				out.write("\t\t<response>");
				out.write(this.response);
				out.write("</response>\n");
				out.flush();
			}
			
			@Override
			public String toString(){
				return this.response;
			}
		}//ShortAnswer class
	}//Question
	
///////////////////////////////////////////////////////////////////
	/*
	 * Classes for parsing Assignments and AssignmentSubmissions
	 */
	private static class AssignmentParser{
		
		protected static ArrayList<Question> parseAssignmentXML(String filename) throws ParserConfigurationException, SAXException, IOException{
			if(filename == null || filename.length() <= 0) return null;
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();
			AssignmentHandler handler = new AssignmentHandler(saxParser.getXMLReader());
			saxParser.parse(filename, handler);
			return handler.getQuestions();
		}
		
		private static class AssignmentHandler extends DefaultHandler{

			private XMLReader reader;
			private ArrayList<Question> questions;
			
			protected AssignmentHandler(XMLReader reader){
				super();
				this.reader = reader;
				this.questions = new ArrayList<Question>(0);
			}
			
			protected ArrayList<Question> getQuestions(){
				return this.questions;
			}
			
			protected void addQuestion(Question q){
				if(q == null) return;
				this.questions.add(q);
			}
			
			@Override
			public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
				super.startElement(uri, localName, qName, attributes);
				if(qName.equals("question")){
					String type = attributes.getValue("type");
					String num = attributes.getValue("number");
					if(type != null && num!=null){
						int n = Integer.parseInt(num);
						if(type.equals("multiple_choice"))
							this.reader.setContentHandler(new MultipleChoiceHandler(this.reader, this, n));
						else if(type.equals("short_answer"))
							this.reader.setContentHandler(new ShortAnswerHandler(this.reader, this, n));
						else if(type.equals("file_submission"))
							this.reader.setContentHandler(new FileSubmissionHandler(this.reader, this, n));
					}				
				}
			}
		}//AssignmentHandler class
		
		private static final class MultipleChoiceHandler extends DefaultHandler {
			
			private XMLReader reader;
			private AssignmentHandler parent;
			private Question question;
			private Question.MultipleChoiceAnswer answer;
			private StringBuilder content;
			private boolean correctAnswer = false;
			private boolean selectedAnswer = false;
			
			protected MultipleChoiceHandler(XMLReader reader, AssignmentHandler parent, int num){
				super();
				this.reader = reader;
				this.parent = parent;
				this.question = new Question();
				this.answer = new Question.MultipleChoiceAnswer();
				this.answer.setNum(num);
				this.content = new StringBuilder();
			}//QuestionHandler

			@Override
			public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
				super.startElement(uri, localName, qName, attributes);
				if(qName.equals("prompt")){
					this.content.setLength(0);
				}else if(qName.equals("choice")){
					this.content.setLength(0);
					String correctAttrib = attributes.getValue("correct");
					String selectAtrrib = attributes.getValue("selected");
					this.correctAnswer = (correctAttrib == null || !correctAttrib.equals("true")) ? false : true;
					this.selectedAnswer = (selectAtrrib == null || !selectAtrrib.equals("true")) ? false : true;
				}else throw new SAXException("Malformed XML document");
			}

			@Override
			public void endElement(String uri, String localName, String qName) throws SAXException {
				super.endElement(uri, localName, qName);
				if(qName.equals("prompt")){
					this.question.setQuestion(this.content.toString());
				}else if(qName.equals("choice")){
					if(this.correctAnswer && this.selectedAnswer)
						this.answer.addCorrectAndSelectedAnswer(this.content.toString());
					else if(this.correctAnswer)
						this.answer.addCorrectAnswer(this.content.toString());
					else if(this.selectedAnswer)
						this.answer.addSelectedAnswer(this.content.toString());
					else 
						this.answer.addAnswer(this.content.toString());
					this.correctAnswer = this.selectedAnswer = false;
				}else if(qName.equals("question")){
					this.question.setAnswer(this.answer);
					this.parent.addQuestion(this.question);
					this.reader.setContentHandler(parent);
				}else throw new SAXException("Malformed XML document");
			}

			@Override
			public void characters(char[] ch, int start, int length) throws SAXException {
				super.characters(ch, start, length);
				this.content.append(ch, start, length);
			}
		}//MultipleChoiceHandler class
		
		private static class ShortAnswerHandler extends DefaultHandler {
			
			private XMLReader reader;
			private AssignmentHandler parent;
			private Question question;
			private Question.ShortAnswer answer;
			private StringBuilder content;
			
			protected ShortAnswerHandler(XMLReader reader, AssignmentHandler parent, int num){
				super();
				this.reader = reader;
				this.parent = parent;
				this.content = new StringBuilder();
				this.question = new Question();
				this.answer = new Question.ShortAnswer();
				this.answer.setNum(num);
			}//ShortAnswerHandler

			@Override
			public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
				super.startElement(uri, localName, qName, attributes);
				if(qName.equals("prompt")){
					this.content.setLength(0);
				}else if(qName.equals("response")){
					this.content.setLength(0);
				}else throw new SAXException("Malformed XML document");
			}

			@Override
			public void endElement(String uri, String localName, String qName) throws SAXException {
				super.endElement(uri, localName, qName);
				if(qName.equals("prompt")){
					this.question.setQuestion(this.content.toString());
				}else if(qName.equals("response")){
					this.answer.setResponse(this.content.toString());
				}else if(qName.equals("question")){
					this.question.setAnswer(this.answer);
					this.parent.addQuestion(this.question);
					this.reader.setContentHandler(this.parent);
				}else throw new SAXException("Malformed XML document");
			}

			@Override
			public void characters(char[] ch, int start, int length) throws SAXException {
				super.characters(ch, start, length);
				this.content.append(ch, start, length);
			}
		}//ShortAnswerHandler
		
		private static final class FileSubmissionHandler extends DefaultHandler {
			
			private XMLReader reader;
			private AssignmentHandler parent;
			private Question question;
			private Question.FileSubmissionAnswer answer;
			private StringBuilder content;
			
			protected FileSubmissionHandler(XMLReader reader, AssignmentHandler parent, int num){
				super();
				this.reader = reader;
				this.parent = parent;
				this.content = new StringBuilder();
				this.question = new Question();
				this.answer = new Question.FileSubmissionAnswer();
				this.answer.setNum(num);
			}//FileSubmissionHandler

			@Override
			public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
				super.startElement(uri, localName, qName, attributes);
				if(qName.equals("prompt")){
					this.content.setLength(0);
				}else if(qName.equals("file")){
					this.content.setLength(0);
				}else throw new SAXException("Malformed XML document");
			}

			@Override
			public void endElement(String uri, String localName, String qName) throws SAXException {
				super.endElement(uri, localName, qName);
				if(qName.equals("prompt")){
					this.question.setQuestion(this.content.toString());
				}else if(qName.equals("file")){
					this.answer.setFileName(this.content.toString());
				}else if(qName.equals("question")){
					this.question.setAnswer(this.answer);
					this.parent.addQuestion(this.question);
					this.reader.setContentHandler(this.parent);
				}else throw new SAXException("Malformed XML document");
			}

			@Override
			public void characters(char[] ch, int start, int length) throws SAXException {
				super.characters(ch, start, length);
				this.content.append(ch, start, length);
			}
		}//FileSubmissionAnswerHandler	
	}//AssignmentParser class
///////////////////////////////////////////////////////////////////
}//Assignment