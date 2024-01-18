package il.ac.tau.cs.sw1.trivia;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class TriviaGUI {

	private static final int MAX_ERRORS = 3;
	private Shell shell;
	private Label scoreLabel;
	private Composite questionPanel;
	private Label startupMessageLabel;
	private Font boldFont;
	private String lastAnswer = "";
	int score = 0;
	int wrongCounter = 0;
	int curr = 0;
	List<String[]> arrayList;

	// Currently visible UI elements.
	Label instructionLabel;
	Label questionLabel;
	private List<Button> answerButtons = new LinkedList<>();
	private Button passButton;
	private Button fiftyFiftyButton;
	private String fileLocation;
	int numOfQuestions = 0;
	ArrayList<String> answers;
	int numOfAnswers = 0;
	boolean usedPass = false;
	boolean usedFifty = false;
	public void open() {
		createShell();
		runApplication();
	}

	/**
	 * Creates the widgets of the application main window
	 */
	private void createShell() {
		Display display = Display.getDefault();
		shell = new Shell(display);
		shell.setText("Trivia");

		// window style
		Rectangle monitor_bounds = shell.getMonitor().getBounds();
		shell.setSize(new Point(monitor_bounds.width / 3,
				monitor_bounds.height / 3));
		shell.setLayout(new GridLayout());

		FontData fontData = new FontData();
		fontData.setStyle(SWT.BOLD);
		boldFont = new Font(shell.getDisplay(), fontData);

		// create window panels
		createFileLoadingPanel();
		createScorePanel();
		createQuestionPanel();
	}

	/**
	 * Creates the widgets of the form for trivia file selection
	 */
	private void createFileLoadingPanel() {
		final Composite fileSelection = new Composite(shell, SWT.NULL);
		fileSelection.setLayoutData(GUIUtils.createFillGridData(1));
		fileSelection.setLayout(new GridLayout(4, false));

		final Label label = new Label(fileSelection, SWT.NONE);
		label.setText("Enter trivia file path: ");

		// text field to enter the file path
		final Text filePathField = new Text(fileSelection, SWT.SINGLE
				| SWT.BORDER);
		filePathField.setLayoutData(GUIUtils.createFillGridData(1));

		// "Browse" button
		final Button browseButton = new Button(fileSelection, SWT.PUSH);
		browseButton.setText("Browse");
		browseButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				fileLocation = GUIUtils.getFilePathFromFileDialog(shell);
				if(fileLocation != null)
					filePathField.setText(fileLocation);
			}
		});

		// "Play!" button
		final Button playButton = new Button(fileSelection, SWT.PUSH);
		playButton.setText("Play!");
		playButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				newGame();
				try {
					if(fileLocation == null)
						return;
					String[][] array = getQuestionsArray(new File(fileLocation));
					if(array.length == 0) {
						gameOver();
						return;
					}
					arrayList = Arrays.asList(array);
					Collections.shuffle(arrayList);
					numOfQuestions = array.length;
					answers = new ArrayList<>(Arrays.asList(arrayList.get(curr)));
					answers.remove(curr);
					updateQuestionPanel(arrayList.get(curr)[0], answers);
				} catch (IOException ex) {
					throw new RuntimeException(ex);
				}
			}
		});
	}
	public String[][] getQuestionsArray(File file) throws IOException {
		return new Questions(file).getArr();
	}
	/**
	 * Creates the panel that displays the current score
	 */
	private void createScorePanel() {
		Composite scorePanel = new Composite(shell, SWT.BORDER);
		scorePanel.setLayoutData(GUIUtils.createFillGridData(1));
		scorePanel.setLayout(new GridLayout(2, false));

		final Label label = new Label(scorePanel, SWT.NONE);
		label.setText("Total score: ");

		// The label which displays the score; initially empty
		scoreLabel = new Label(scorePanel, SWT.NONE);
		scoreLabel.setLayoutData(GUIUtils.createFillGridData(1));
		scoreLabel.setText(String.valueOf(score));
	}

	/**
	 * Creates the panel that displays the questions, as soon as the game
	 * starts. See the updateQuestionPanel for creating the question and answer
	 * buttons
	 */
	private void createQuestionPanel() {
		questionPanel = new Composite(shell, SWT.BORDER);
		questionPanel.setLayoutData(new GridData(GridData.FILL, GridData.FILL,
				true, true));
		questionPanel.setLayout(new GridLayout(2, true));

		// Initially, only displays a message
		startupMessageLabel = new Label(questionPanel, SWT.NONE);
		startupMessageLabel.setText("No question to display, yet.");
		startupMessageLabel.setLayoutData(GUIUtils.createFillGridData(2));
	}

	/**
	 * Serves to display the question and answer buttons
	 */
	private void updateQuestionPanel(String question, List<String> answers) {
		// Save current list of answers.
		String currentAnswer = answers.get(0);
		Collections.shuffle(answers);
		// clear the question panel
		Control[] children = questionPanel.getChildren();
		for (Control control : children) {
			control.dispose();
		}

		// create the instruction label
		instructionLabel = new Label(questionPanel, SWT.CENTER | SWT.WRAP);
		instructionLabel.setText(lastAnswer + "Answer the following question:");
		instructionLabel.setLayoutData(GUIUtils.createFillGridData(2));

		// create the question label
		questionLabel = new Label(questionPanel, SWT.CENTER | SWT.WRAP);
		questionLabel.setText(question);
		questionLabel.setFont(boldFont);
		questionLabel.setLayoutData(GUIUtils.createFillGridData(2));

		// create the answer buttons
		answerButtons.clear();
		for (int i = 0; i < 4; i++) {
			Button answerButton = new Button(questionPanel, SWT.PUSH | SWT.WRAP);
			answerButton.setText(answers.get(i));
			if(answers.get(i).equals(currentAnswer))
				answerButton.addSelectionListener(new rightAnswer());
			else
				answerButton.addSelectionListener(new wrongAnswer());
			GridData answerLayoutData = GUIUtils.createFillGridData(1);
			answerLayoutData.verticalAlignment = SWT.FILL;
			answerButton.setLayoutData(answerLayoutData);
			
			answerButtons.add(answerButton);
		}

		// create the "Pass" button to skip a question
		passButton = new Button(questionPanel, SWT.PUSH);
		passButton.setText("Pass");
		GridData data = new GridData(GridData.END, GridData.CENTER, true,
				false);
		data.horizontalSpan = 1;
		passButton.setLayoutData(data);
		if(usedPass && score <=0) {
			passButton.setEnabled(false);
		}
		passButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(usedPass && score <= 0)
					return;
				curr++;
				if(!usedPass){
					usedPass = true;
				}else{
					score--;
					scoreLabel.setText("Total score: " + score);
				}
				if(curr == numOfQuestions) {
					gameOver();
					return;
				}
				setAnswers(new ArrayList<>(Arrays.asList(arrayList.get(curr))));
				ArrayList<String> answer = getAnswers();
				answer.remove(0);
				updateQuestionPanel(arrayList.get(curr)[0], answer);


			}
		});
		
		// create the "50-50" button to show fewer answer options
		fiftyFiftyButton = new Button(questionPanel, SWT.PUSH);
		fiftyFiftyButton.setText("50-50");
		data = new GridData(GridData.BEGINNING, GridData.CENTER, true,
				false);
		data.horizontalSpan = 1;
		fiftyFiftyButton.setLayoutData(data);
		if(usedFifty && score <= 0)
			fiftyFiftyButton.setEnabled(false);
		fiftyFiftyButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(usedFifty && score <= 0)
					return;
				ArrayList<String> fiftyFiftyAnswers = new ArrayList<>(answers);
				fiftyFiftyAnswers.remove(currentAnswer);
				Collections.shuffle(fiftyFiftyAnswers);
				ArrayList<String> givenFiftyFiftyAnswers = new ArrayList<>();
				givenFiftyFiftyAnswers.add(currentAnswer);
				givenFiftyFiftyAnswers.add(fiftyFiftyAnswers.get(0));
				Collections.shuffle(givenFiftyFiftyAnswers);
				for(Button b : answerButtons){
					if(!givenFiftyFiftyAnswers.contains(b.getText()))
						b.setEnabled(false);
				}
				if(usedFifty)
					score--;
				scoreLabel.setText("Total score: " + score);
				usedFifty = true;
				fiftyFiftyButton.setEnabled(false);
			}
		});
		// two operations to make the new widgets display properly
		questionPanel.pack();
		questionPanel.getParent().layout();
	}

	/**
	 * Opens the main window and executes the event loop of the application
	 */
	private void runApplication() {
		shell.open();
		Display display = shell.getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
		boldFont.dispose();
	}
	private ArrayList<String> getAnswers(){
		return answers;
	}
	private void setAnswers(ArrayList<String> s){
		this.answers = s;
	}
	public class rightAnswer implements SelectionListener{
		@Override
		public void widgetSelected(SelectionEvent selectionEvent) {
			lastAnswer = "Correct! ";
			score += 3;
			curr++;
			wrongCounter = 0;
			numOfAnswers++;
			if(curr == numOfQuestions)
				gameOver();
			else {
				scoreLabel.setText("Total score: " + score);
				answers = new ArrayList<>(Arrays.asList(arrayList.get(curr)));
				answers.remove(0);
				updateQuestionPanel(arrayList.get(curr)[0], answers);
			}
		}

		@Override
		public void widgetDefaultSelected(SelectionEvent selectionEvent) {

		}
	}
	public class wrongAnswer implements SelectionListener{

		@Override
		public void widgetSelected(SelectionEvent selectionEvent) {
			lastAnswer = "Wrong.. ";
			score -= 2;
			wrongCounter++;
			curr++;
			scoreLabel.setText("Total score: " + score);
			numOfAnswers++;
			if(wrongCounter == 3 || curr == numOfQuestions){
				gameOver();
			}else {
				answers = new ArrayList<>(Arrays.asList(arrayList.get(curr)));
				answers.remove(0);
				updateQuestionPanel(arrayList.get(curr)[0], answers);
			}

		}

		@Override
		public void widgetDefaultSelected(SelectionEvent selectionEvent) {

		}
	}
	public void gameOver(){
		fiftyFiftyButton.setEnabled(false);
		passButton.setEnabled(false);
		for(Button b : answerButtons)
			b.setEnabled(false);
		GUIUtils.showInfoDialog(shell,"GAME OVER","Your final score is " + score + " after " + numOfAnswers +" questions.");

	}

	public void newGame(){
		score = 0;
		curr = 0;
		wrongCounter = 0;
		arrayList = null;
		scoreLabel.setText("Total score: " + score);
		lastAnswer = "";
		answerButtons = new LinkedList<>();
		numOfQuestions = 0;
		answers = null;
		numOfAnswers = 0;
		usedPass = false;
		usedFifty = false;
	}
}
