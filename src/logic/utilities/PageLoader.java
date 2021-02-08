package logic.utilities;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayDeque;
import java.util.Deque;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Pair;
import logic.view.menu.element.NavigationBar;
import logic.view.menu.element.StatusBar;
import logic.view.page.AssignmentPageView;
import logic.view.page.CoursePageView;
import logic.view.page.LessonPageView;
import logic.view.page.QuestionPageView;
import logic.view.page.ScheduledPageView;

public class PageLoader {

	private static PageLoader instance = null;
	private static Stage stage;
	private static Page page = null;
	private static Object obj = null;
	private static Deque<Pair<Page, Object>> stack = new ArrayDeque<>();
	
	private FXMLLoader loader;

	private Background background = new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY));

	private PageLoader() {

	}

	public static PageLoader getInstance() {
		if (instance == null) {
			instance = new PageLoader();
		}
		return instance;
	}

	public static Page getPage() {
		return page;
	}
	
	public static void setPage(Page page) {
		PageLoader.page = page;
	}
	
	public static Stage getStage() {
		return stage;
	}

	public static Object getObject() {
		return obj;
	}
	
	public static void setObject(Object obj) {
		PageLoader.obj = obj;
	}
	
	public static void setStage(Stage stage) {
		PageLoader.stage = stage;
	}
	
	public static void addFirst(Pair<Page, Object> pair) {
		stack.addFirst(pair);
	}
	
	public static Pair<Page, Object> getFirst() {
		return stack.removeFirst();
	}
	
	public void goBack() throws IOException {
		
		if (stack.peek().getKey() == null || stack.isEmpty()) {
			System.out.println("usa logout");
			return;
		}
		
		Pair<Page, Object> last = getFirst();
		
		if (last.getValue() == null) {
			buildPage(last.getKey());
		}
		
		else {
			buildPage(last.getKey(), last.getValue());
			
		}
		
		if (!stack.isEmpty()) {
			getFirst();
		}
	}

	// BuildPage and pass a bean to the page controller
	public void buildPage(Page page, Object obj) throws IOException {
		
		addFirst(new Pair<>(PageLoader.getPage(), PageLoader.getObject()));
		
		PageLoader.setPage(page);
		PageLoader.setObject(obj);
		
		switch (page) {		// TODO high coupling (cast dal pageloader)

		case COURSE:
			loadPage();
			CoursePageView coursePageView = new CoursePageView();
			loader.setController(coursePageView);
			configPage(loader.load());
			coursePageView.setBean(obj);
			break;

		case SCHEDULED_LESSONS:
			loadPage();
			ScheduledPageView scheduledPageView = new ScheduledPageView();
			loader.setController(scheduledPageView);
			configPage(loader.load());
			scheduledPageView.setLessonPage(obj);
			break;

		case SCHEDULED_EXAMS:
			loadPage();
			ScheduledPageView scheduledExamPageView = new ScheduledPageView();
			loader.setController(scheduledExamPageView);
			configPage(loader.load());
			scheduledExamPageView.setExamPage(obj);
			break;

		case LESSON:
			loadPage();
			LessonPageView lessonPageView = new LessonPageView();
			loader.setController(lessonPageView);
			configPage(loader.load());
			lessonPageView.setBean(obj);
			break;

		case QUESTION:
			loadPage();
			QuestionPageView questionPageView = new QuestionPageView();
			loader.setController(questionPageView);
			configPage(loader.load());
			questionPageView.setBean(obj);
			break;
			
		case ASSIGNMENT:
			loadPage();
			AssignmentPageView assignmentPageView = new AssignmentPageView();
			loader.setController(assignmentPageView);
			configPage(loader.load());
			assignmentPageView.setBean(obj);
			break;

		default:
			loadPage();
			configPage(loader.load());
			break;
		}
	}

	// Build a page without pass a bean
	public void buildPage(Page page) throws IOException {
		
		addFirst(new Pair<>(PageLoader.getPage(), PageLoader.getObject()));
		
		PageLoader.setPage(page);
		PageLoader.setObject(null);
		
		switch (page) {
		
		case SIGNUP:
			loadPageNoNavBar();
			break;

		case LOGIN:
			loadPageNoNavBar();
			break;
			
		default:
			loadPage();
			configPage(loader.load());
			break;
		}
	}

	private void loadPage() throws IOException {
		URL url = new File(page.getRes()).toURI().toURL();
		loader = new FXMLLoader(url);
	}

	private NavigationBar configNavBar() throws IOException {
		return NavigationBar.getInstance();
	}

	private HBox configStatusBar() throws IOException {
		// Reset StatusBar instance every time in order to set label and user avatar properly
		StatusBar.reset();
		HBox statusBarHBox = new HBox(StatusBar.getInstance());
		statusBarHBox.setAlignment(Pos.TOP_RIGHT);
		HBox.setMargin(StatusBar.getInstance(), new Insets(54.0, 80.0, 0, 0));
		return statusBarHBox;
	}

	private void configPage(Parent pageView) throws IOException {
		VBox vBox = new VBox();
		vBox.getChildren().addAll(configStatusBar(), pageView);
		HBox mainLayoutHBox = new HBox(configNavBar(), vBox);
		HBox.setMargin(NavigationBar.getInstance(), new Insets(24.0, 72.0, 24.0, 32.0));
		mainLayoutHBox.setBackground(background); // Set white color to the scene

		Scene scene = new Scene(mainLayoutHBox);
		stage.setScene(scene);
		stage.setTitle(page.getStageTitle());
		stage.show();
	}

	private void loadPageNoNavBar() throws IOException {
		URL url = new File(page.getRes()).toURI().toURL();
		loader = new FXMLLoader(url);
		Parent parent = loader.load();

		Scene scene = new Scene(parent);
		stage.setScene(scene);
		stage.setTitle(page.getStageTitle());
		stage.show();
	}

	public Object getController() {
		return loader.getController();
	}
}
