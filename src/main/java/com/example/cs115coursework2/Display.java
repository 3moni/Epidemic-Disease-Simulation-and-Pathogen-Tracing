package com.example.cs115coursework2;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.*;

/**
 * Class to create the Display
 */
public class Display extends Application {

    /**
     * DO NOT EDIT
     * Variables relating to the display
     */
    private Pane root;
    private VBox vBox;
    private Button spreadButton;
    private SimpleStringProperty spreadButtonText = new SimpleStringProperty("Start Spread");
    private VBox pathogenBox = new VBox();

    /**
     * Button for use in Task 8
     */
    private Button receiptButton;

    /**
     * DO NOT EDIT
     * Constants for the display
     */
    private final int WINDOW_SIZE_WIDTH = 1000;
    private final int WINDOW_SIZE_HEIGHT = 1200;
    private final int IMAGE_HW = 30;
    private final int PATH_OFFSET = 15;

    /**
     * DO NOT EDIT
     * Method to launch display and run program
     *
     * @param args from command line
     */
    public static void main(String[] args) {
        launch();
    }

    /**
     * DO NOT EDIT
     * Method to set up stage
     *
     * @param primaryStage stage to set up
     */
    @Override
    public void start(Stage primaryStage) {
        root = new Pane();
        root.setLayoutX(10);
        root.setLayoutY(10);
        root.setMaxSize(WINDOW_SIZE_WIDTH, WINDOW_SIZE_HEIGHT);

        HBox hBox = new HBox();
        vBox = new VBox();
        vBox.setSpacing(10);

        // Students implement this
        initialize();

        //these are pre-existing methods not to be changed
        addKey(); //key is added to vBox
        addPathogenList(); //pathogenList is added to vBox


        Image map = new Image("map.png");
        ImageView mapImageView = new ImageView();
        mapImageView.setLayoutX(200);
        mapImageView.setFitHeight(1000);
        mapImageView.setFitWidth(680);
        mapImageView.setImage(map);

        //arrange the display
        hBox.getChildren().add(vBox);
        root.getChildren().add(mapImageView);

        addDiseaseCentres(); //map of centres

        root.getChildren().add(hBox);

        //Set up and show scene
        Scene scene = new Scene(root, WINDOW_SIZE_WIDTH, WINDOW_SIZE_HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Disease Modelling");
        primaryStage.show();
    }

    /**
     * DO NOT EDIT
     * Method to add a key to the screen which displays all types of pathogens and a button to show the next pathogen.
     */
    private void addKey() {
        Label keyLabel = new Label("Key");
        Image bactImage = new Image("bacterium.png");
        ImageView bactImageView = new ImageView();
        bactImageView.setFitHeight(IMAGE_HW);
        bactImageView.setFitWidth(IMAGE_HW);
        bactImageView.setImage(bactImage);

        Image virusImage = new Image("virus.png");
        ImageView virusImageView = new ImageView();
        virusImageView.setFitHeight(IMAGE_HW);
        virusImageView.setFitWidth(IMAGE_HW);
        virusImageView.setImage(virusImage);

        Image phageImage = new Image("phage.png");
        ImageView phageImageView = new ImageView();
        phageImageView.setFitHeight(IMAGE_HW);
        phageImageView.setFitWidth(IMAGE_HW);
        phageImageView.setImage(phageImage);

        Label bacteriaLabel = new Label("Bacteria");
        Label virusLabel = new Label("Virus");
        Label phageLabel = new Label("Phage");

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.add(keyLabel, 1, 1);
        gridPane.add(bactImageView, 1, 2);
        gridPane.add(virusImageView, 1, 3);
        gridPane.add(phageImageView, 1, 4);
        gridPane.add(bacteriaLabel, 2, 2);
        gridPane.add(virusLabel, 2, 3);
        gridPane.add(phageLabel, 2, 4);
        vBox.getChildren().add(gridPane);

        // Set up of spread button.
        spreadButton = new Button();
        spreadButton.textProperty().bind(spreadButtonText);
        spreadButton.setOnAction(e -> nextPathogen());

        if (Data.getPathogens() != null && Data.getPathogens().isEmpty()) {
            updatePathogenButtonText("No more pathogens");
            spreadButton.setDisable(true);
        }
        vBox.getChildren().add(spreadButton);

        //students program this method
        addButtonTask(vBox);

        Label pathogenLabel = new Label();
        pathogenLabel.setText("Pathogens");
        vBox.getChildren().add(pathogenLabel);
    }

    /**
     * DO NOT EDIT
     * Method to update the buttonText
     *
     * @param text to update button to
     */
    private void updatePathogenButtonText(String text) {
        spreadButtonText.set(text);
    }

    /**
     * DO NOT EDIT
     * This method lists the pathogens in the pathogen stack on the display according to their type.
     */
    private void addPathogenList() {
        pathogenBox.setSpacing(5.00);
        if (Data.getPathogens() != null) {
            LinkedList<Pathogen> pathogens = new LinkedList<>(Data.getPathogens());
            Collections.reverse(pathogens);
            for (Pathogen pathogen : pathogens) {
                pathogenBox.getChildren().add(getImageView(pathogen));

            }
            // Create a ScrollPane and set the VBox as its content
            ScrollPane scrollPane = new ScrollPane();
            scrollPane.setContent(pathogenBox);

            // Set the preferred size of the scroll pane
            scrollPane.setPrefSize(150, 500);
            vBox.getChildren().addAll(scrollPane);
        }
    }

    /**
     * DO NOT EDIT
     * This method adds the disease centres from the database to the display.
     */
    private void addDiseaseCentres() {
        if (Data.getDiseaseCentres() != null) {
            for (DiseaseCentre diseaseCentre : Data.getDiseaseCentres()) {
                ImageView diseaseCentreImageView = getDiseaseCentreImageView(diseaseCentre);
                root.getChildren().add(diseaseCentreImageView);
                for (DiseaseCentre neighbour : diseaseCentre.getConnections()) {
                    Line path = new Line(diseaseCentre.getX() + PATH_OFFSET, diseaseCentre.getY() + PATH_OFFSET,
                            neighbour.getX() + PATH_OFFSET, neighbour.getY() + PATH_OFFSET);
                    path.setStroke(Color.RED);
                    root.getChildren().add(path);
                }
            }
        }
    }

    /**
     * DO NOT EDIT
     * Sets the image view based on the diseaseCentre and it's values
     *
     * @param diseaseCentre to set image for
     * @return Image View with correct image being used
     */
    private ImageView getDiseaseCentreImageView(DiseaseCentre diseaseCentre) {
        Image diseaseCentreImage;
        if (diseaseCentre.isResearch()) {
            diseaseCentreImage = new Image("laboratory.png");
        } else {
            diseaseCentreImage = new Image("centre.png");
        }
        ImageView diseaseCentreImageView = new ImageView();
        diseaseCentreImageView.setFitHeight(IMAGE_HW + 10);
        diseaseCentreImageView.setFitWidth(IMAGE_HW + 10);
        diseaseCentreImageView.setImage(diseaseCentreImage);
        diseaseCentreImageView.setLayoutX(diseaseCentre.getX());
        diseaseCentreImageView.setLayoutY(diseaseCentre.getY());
        return diseaseCentreImageView;
    }

    /**
     * DO NOT EDIT
     * This method is called with the pathogen button is pressed. It spreads the pathogens from the pathogen
     * stack one by one until complete.
     */
    private void nextPathogen() {
        // Pop the first pathogen and start its journey
        Pathogen pathogen = Data.getPathogens().pop();
        System.out.println(pathogen.toString());
        pathogenBox.getChildren().remove(0);
        if (Data.getPathogens().isEmpty()) {
            updatePathogenButtonText("No more pathogens");
            spreadButton.setDisable(true);
        } else {
            updatePathogenButtonText("Next pathogen");
        }
        if (pathogen != null) {
            // Spread the pathogens and start animation
            // After animation completes, check for next pathogen in the queue
            spreadPathogen(pathogen, this::nextPathogen);
        }
    }

    /**
     * DO NOT EDIT
     * Method to create an image view for a pathogen
     *
     * @param pathogen to create an image for
     * @return imageview for the pathogen
     */
    private ImageView getImageView(Pathogen pathogen) {
        Image image = null;
        if (pathogen.getClass().getSimpleName().equals("Bacteria")) {
            image = new Image("bacterium.png");
        } else if (pathogen.getClass().getSimpleName().equals("Virus")) {
            image = new Image("virus.png");
        } else if (pathogen.getClass().getSimpleName().equals("Phage")) {
            image = new Image("phage.png");
        }
        ImageView pathogenImageView = new ImageView();
        pathogenImageView.setFitHeight(IMAGE_HW);
        pathogenImageView.setFitWidth(IMAGE_HW);
        pathogenImageView.setImage(image);
        return pathogenImageView;
    }

    /**
     * DO NOT EDIT
     * This method takes in a pathogen object, ensures the correct image is output to the display, and calls getPath
     * to animate the image along the spread path.
     *
     * @param pathogen to be spread on display
     * @param onFinish to track animation
     */
    private void spreadPathogen(Pathogen pathogen, Runnable onFinish) {
        ImageView pathogenImageView = getImageView(pathogen);
        List<DiseaseCentre> path = getPath(pathogen.getEpicentre(), pathogen.getEndPoint());
        if (!path.isEmpty()) {
            // Add ImageView to the root pane
            root.getChildren().add(pathogenImageView);
            for (int i = 0; i < path.size() - 1; i++) {
                pathogen.spread(path.get(i));
                animateImageView(pathogenImageView, path, 0);
            }
            pathogen.spread(pathogen.getEndPoint());
            if (Data.getSpreadPathogens() != null) {
                Data.getSpreadPathogens().push(pathogen);
            }
        }
    }

    /**
     * DO NOT EDIT
     * This method constructs a path from one disease centre to another, to find a route that a pathogen can take.
     * If no path exists then an empty list is returned. This route is not necessarily the shortest.
     *
     * @param start disease centre
     * @param end   disease centre
     * @return path as a list
     */
    public static List<DiseaseCentre> getPath(DiseaseCentre start, DiseaseCentre end) {
        ArrayList<DiseaseCentre> visited = new ArrayList<>();
        Stack<DiseaseCentre> stack = new Stack<>();
        Map<DiseaseCentre, DiseaseCentre> parentMap = new HashMap<>();

        stack.push(start);
        visited.add(start);

        while (!stack.isEmpty()) {
            DiseaseCentre current = stack.pop();
            if (current == end) {
                List<DiseaseCentre> path = new ArrayList<>();
                while (end != start) {
                    path.add(end);
                    end = parentMap.get(end);
                }
                path.add(start); // Add start facility
                Collections.reverse(path);
                return path;
            }
            for (DiseaseCentre connection : current.getConnections()) {
                if (!visited.contains(connection)) {
                    stack.push(connection);
                    visited.add(connection);
                    parentMap.put(connection, current);
                }
            }
        }
        return Collections.emptyList(); // No path found
    }

    /**
     * DO NOT EDIT
     * This method is called recursively with an imageView and a path. An animation is then played until the imageView
     * has travelled along the entire path (using animateTransition method).
     * The index is used to track how many pairs of transitions there are in the path upon which the image can travel.
     *
     * @param imageView the image to animate
     * @param path      the path to travel along
     * @param index     the index, to keep track of the pairs to travel from/to
     */
    private void animateImageView(ImageView imageView, List<DiseaseCentre> path, int index) {
        if (index < path.size() - 1) {
            DiseaseCentre currentFacility = path.get(index);
            DiseaseCentre nextFacility = path.get(index + 1);

            // Animate the transition from current facility to next facility
            animateTransition(imageView, currentFacility.getX(), currentFacility.getY(),
                    nextFacility.getX(), nextFacility.getY(), () -> {
                        // Recursively call animateImageView to animate the next segment
                        animateImageView(imageView, path, index + 1);
                    });
        } else {
            // Animation completed for the entire path
            root.getChildren().remove(imageView);
        }
    }

    /**
     * DO NOT EDIT
     * This animates the transition along a path.
     *
     * @param imageView  the image to animate
     * @param startX     the X coord starting point of the animation
     * @param startY     the Y coord starting point of the animation
     * @param endX       the X coord ending point of the animation
     * @param endY       the Y coord starting point of the animation
     * @param onFinished triggers the next animation if there is a longer path than 1 pair.
     */
    private void animateTransition(ImageView imageView, double startX, double startY,
                                   double endX, double endY, Runnable onFinished) {
        // Create TranslateTransition for the current segment
        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setNode(imageView);
        translateTransition.setDuration(Duration.seconds(3)); // Adjust duration as needed
        translateTransition.setFromX(startX);
        translateTransition.setFromY(startY);
        translateTransition.setToX(endX);
        translateTransition.setToY(endY);
        if (onFinished != null) {
            // Set onFinished event to trigger the next animation
            translateTransition.setOnFinished(event -> onFinished.run());
        }
        // Play the animation
        translateTransition.play();
    }

    /**
     * DO NOT EDIT ANY CODE ABOVE THIS COMMENT. There are some methods below which you need to complete.
     */

    /**
     * Method required for display to run.
     * Complete this in Task 6
     */
    private void initialize() {
        Data.readDiseaseCentres();
        Data.addConnections();
        Data.readPathogens();
    }

    /**
     * Method required for display to run.
     * Complete this in Task 8.
     * @param vBox to add button to
     */
    private void addButtonTask(VBox vBox) {
        receiptButton = new Button("Print Traces");

        receiptButton.setOnAction(e -> Data.printTraces());

        vBox.getChildren().add(receiptButton);
    }
}