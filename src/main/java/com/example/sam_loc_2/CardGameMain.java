package com.example.sam_loc_2;

import javafx.animation.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static com.example.sam_loc_2.GameLogic.canBeatPreviousMove;
import static com.example.sam_loc_2.GameLogic.isValidMove;
import static com.example.sam_loc_2.GameManager.addAIPlayer;
import static com.example.sam_loc_2.GameManager.players;

public class CardGameMain extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Tạo ảnh nền
        Image backgroundImage = new Image(getClass().getResource("/anh_nen/nen1.jpg").toExternalForm());
        BackgroundImage background = new BackgroundImage(backgroundImage,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER, new BackgroundSize(500, 500, true, true, true, true));
        Pane root = new Pane();
        root.setBackground(new Background(background));


        // Nhạc nền
        Media music = new Media(getClass().getResource("/anh_nen/nhac_nen.mp3").toExternalForm());
        MediaPlayer mediaPlayer = new MediaPlayer(music);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Lặp lại vô hạn
        mediaPlayer.play();

        // Tạo tiêu đề
        Text title = new Text("_CARDS GAME_");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 50));
        title.setFill(Color.WHITE);
        title.setStroke(Color.BLACK);
        title.setStrokeWidth(2);

        Text subtitle = new Text("NHÓM 1");
        subtitle.setFont(Font.font("Arial", FontWeight.BOLD, 40));
        subtitle.setFill(Color.WHITE);
        subtitle.setStroke(Color.BLACK);
        subtitle.setStrokeWidth(2);

        VBox titleBox = new VBox(10, title, subtitle);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.setLayoutX(212); // Tùy chỉnh vị trí tiêu đề
        titleBox.setLayoutY(50);

        // Tạo các nút
        Button button2 = new Button("Chơi Sâm Lốc");

        button2.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        button2.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");

        VBox buttonBox = new VBox(20, button2);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setLayoutX(320); // Tùy chỉnh vị trí nút
        buttonBox.setLayoutY(250);

        // Xử lý sự kiện nút
        button2.setOnAction(e -> showGameModeSelection(primaryStage)); // Chuyển sang Bước 2

        // Thêm các phần tử vào giao diện
        root.getChildren().addAll(titleBox, buttonBox);

        // Hiển thị cửa sổ chính
        Scene scene = new Scene(root, 800, 600); // Kích thước cửa sổ
        primaryStage.setTitle("Game Bài 3 Cây");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Chuyển sang giao diện chọn chế độ chơi
    private void showGameModeSelection(Stage primaryStage) {
        // Tạo root layout cho Bước 2
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);

        // Tạo các nút chế độ chơi
        Button play1 = new Button("CHƠI 1 NGƯỜI");
        Button play2 = new Button("CHƠI 2 NGƯỜI");
        Button play3 = new Button("CHƠI 3 NGƯỜI");
        Button play4 = new Button("CHƠI 4 NGƯỜI");

        play1.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        play2.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        play3.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        play4.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        play1.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        play2.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        play3.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        play4.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");

        // Thêm sự kiện cho từng nút
        play1.setOnAction(e -> showPlayerInputDialog(primaryStage, 1));
        play2.setOnAction(e -> showPlayerInputDialog(primaryStage, 2));
        play3.setOnAction(e -> showPlayerInputDialog(primaryStage, 3));
        play4.setOnAction(e -> showPlayerInputDialog(primaryStage, 4));

        root.getChildren().addAll(play1, play2, play3, play4);

        // Tạo scene mới và hiển thị
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
    }

    // Hiển thị hộp thoại nhập tên người chơi
    private void showPlayerInputDialog(Stage primaryStage, int numPlayers) {
        // GridPane để tổ chức input
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(10);
        grid.setHgap(10);

        List<TextField> playerNameInputs = new ArrayList<>();

        // Tạo các ô nhập cho từng người chơi
        for (int i = 0; i < numPlayers; i++) {
            Label label = new Label("Tên người chơi " + (i + 1) + ":");
            label.setFont(Font.font("Arial", FontWeight.BOLD, 16));
            TextField textField = new TextField();
            playerNameInputs.add(textField);

            grid.add(label, 0, i);
            grid.add(textField, 1, i);
        }

        // Nút "BẮT ĐẦU"
        Button startButton = new Button("BẮT ĐẦU");
        startButton.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        startButton.setStyle("-fx-background-color: #FF5722; -fx-text-fill: white;");
        grid.add(startButton, 0, numPlayers, 2, 1);
        GridPane.setHalignment(startButton, HPos.CENTER);

        // Xử lý sự kiện bấm nút "BẮT ĐẦU"
        startButton.setOnAction(e -> {
            // Lưu tên người chơi
            List<String> playerNames = new ArrayList<>();
            for (TextField textField : playerNameInputs) {
                String name = textField.getText().trim();
                if (name.isEmpty()) {
                    // Hiển thị cảnh báo nếu không nhập tên
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Cảnh báo");
                    alert.setHeaderText("Thiếu tên người chơi!");
                    alert.setContentText("Vui lòng nhập tên cho tất cả người chơi.");
                    alert.showAndWait();
                    return;
                }
                playerNames.add(name);
            }

            // Chuyển sang giao diện Bước 3
            startGame(primaryStage, playerNames);
        });

        // Tạo scene và hiển thị
        Scene scene = new Scene(grid, 600, 400);
        primaryStage.setScene(scene);
    }

    // Phương thức startGame để tạo giao diện chơi game
    private void startGame(Stage primaryStage, List<String> playerNames) {
        // Nếu chỉ có một người chơi, tự động thêm "AI_Player"
        if (playerNames.size() == 1) {
            playerNames.add("AI_Player"); // Thêm tên để hiển thị
        }

        // Tạo ảnh nền cho cửa sổ game
        Image backgroundImage = new Image(getClass().getResource("/anh_nen/nen3.jpg").toExternalForm());
        BackgroundImage background = new BackgroundImage(backgroundImage,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER, new BackgroundSize(1000, 1000, false, false, false, false));

        // Sử dụng Pane làm root
        Pane root = new Pane();
        root.setBackground(new Background(background));

        // Tạo vị trí hiển thị tên người chơi ở 4 góc
        List<Label> playerLabels = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Label playerLabel = new Label();
            playerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
            playerLabel.setTextFill(Color.BLACK);
            playerLabel.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5); -fx-padding: 5px;");

            // Chỉ hiển thị tên nếu người chơi tồn tại
            if (i < playerNames.size()) {
                playerLabel.setText(playerNames.get(i));
            } else {
                playerLabel.setVisible(false); // Ẩn nhãn nếu không có người chơi
            }
            playerLabels.add(playerLabel);
        }

        // Đặt vị trí của các nhãn
        playerLabels.get(0).relocate(20, 20); // Góc trên trái
        playerLabels.get(1).relocate(660, 20); // Góc trên phải
        playerLabels.get(2).relocate(20, 520); // Góc dưới trái
        playerLabels.get(3).relocate(660, 520); // Góc dưới phải

        // Thêm nhãn vào giao diện
        root.getChildren().addAll(playerLabels);

        // Nút "CHIA BÀI" ở giữa
        Button dealButton = new Button("CHIA BÀI");
        dealButton.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        dealButton.setStyle("-fx-background-color: #FF5722; -fx-text-fill: white; -fx-padding: 10px 20px;");
        dealButton.setLayoutX(350); // Vị trí ngang ở giữa
        dealButton.setLayoutY(270); // Vị trí dọc ở giữa


        // Xử lý sự kiện nút "CHIA BÀI"
        dealButton.setOnAction(e -> {
            dealCards(primaryStage, playerNames, root);
            dealButton.setVisible(false);
        }); // Gọi phương thức chia bài (sẽ triển khai sau)

        root.getChildren().add(dealButton);

        // Tạo scene và hiển thị
        Scene scene = new Scene(root, 1000, 1000);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void dealCards(Stage primaryStage, List<String> playerNames, Pane gamePane) {
        // Khởi tạo đối tượng GameManager với danh sách người chơi
        List<Player> players = new ArrayList<>();
        for (String name : playerNames) {
            // Kiểm tra nếu là AI thì tạo AI_Player
            if (name.equals("AI_Player")) {
                players.add(new AI_Player(name));
            } else {
                players.add(new Player(name));
            }
        }

        GameManager gameManager = new GameManager(players);

        // Tạo bộ bài
        Deck deck = new Deck();
        deck.shuffle();

        // Số người chơi
        int numPlayers = playerNames.size();

        // Danh sách vị trí tương ứng với từng người chơi
        List<Double[]> playerPositions = new ArrayList<>();
        playerPositions.add(new Double[]{100.0, 100.0});  // Vị trí người chơi 1
        playerPositions.add(new Double[]{800.0, 100.0});  // Vị trí người chơi 2
        if (numPlayers > 2) playerPositions.add(new Double[]{100.0, 800.0}); // Người chơi 3
        if (numPlayers > 3) playerPositions.add(new Double[]{800.0, 800.0}); // Người chơi 4

        // Animation timeline để thực hiện hiệu ứng chia bài
        Timeline timeline = new Timeline();

        int totalCards = numPlayers * 10; // Tổng số lá bài cần chia

        // Chia bài cho mỗi người
        for (int i = 0; i < totalCards; i++) {
            int playerIndex = i % numPlayers; // Chọn người chơi theo vòng tròn
            Double[] targetPosition = playerPositions.get(playerIndex);

            // Lấy lá bài từ bộ bài
            Card card = deck.dealOneCard();
            String cardImagePath = "/anh_bai/" + card.getRank().toLowerCase() + "_" + card.getSuit().toLowerCase() + ".png";
            ImageView cardView = new ImageView(new Image(getClass().getResourceAsStream(cardImagePath)));
            cardView.setFitWidth(100);
            cardView.setFitHeight(150);

            // Đặt vị trí lá bài ở giữa màn hình
            cardView.setLayoutX(350);
            cardView.setLayoutY(280);

            gamePane.getChildren().add(cardView);

            // Animation di chuyển lá bài đến vị trí người chơi
            KeyValue kvX = new KeyValue(cardView.layoutXProperty(), targetPosition[0]);
            KeyValue kvY = new KeyValue(cardView.layoutYProperty(), targetPosition[1]);
            KeyFrame kf = new KeyFrame(Duration.seconds(0.5 + i * 0.05), kvX, kvY);
            timeline.getKeyFrames().add(kf);

            // Sau khi di chuyển, thêm lá bài vào tay người chơi
            int currentPlayer = playerIndex;
            timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(0.1 + i * 0.1), e -> {
                players.get(currentPlayer).addCard(card);
            }));
        }

        // Chia lá LAST
        for (int i = 0; i < numPlayers; i++) {
            Card lastCard = new Card("LAST", "Joker");
            String lastCardImagePath = "/anh_bai/mat_sau.png";
            ImageView lastCardView = new ImageView(new Image(getClass().getResourceAsStream(lastCardImagePath)));
            lastCardView.setFitWidth(100);
            lastCardView.setFitHeight(150);

            Double[] targetPosition = playerPositions.get(i);

            lastCardView.setLayoutX(350);
            lastCardView.setLayoutY(280);

            gamePane.getChildren().add(lastCardView);

            KeyValue kvX = new KeyValue(lastCardView.layoutXProperty(), targetPosition[0]);
            KeyValue kvY = new KeyValue(lastCardView.layoutYProperty(), targetPosition[1]);
            KeyFrame kf = new KeyFrame(Duration.seconds(0.05 + (totalCards + i) * 0.05), kvX, kvY);
            timeline.getKeyFrames().add(kf);

            final int playerIndex = i;
            timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(0.2 + (totalCards + i) * 0.2), e -> {
                players.get(playerIndex).addCard(lastCard);
            }));
        }

        // Chạy timeline hiệu ứng chia bài
        timeline.setCycleCount(1);
        timeline.setOnFinished(event -> {
            // Sau khi chia bài xong, bắt đầu lượt chơi
            startTurn(primaryStage, players, gamePane);
        });
        timeline.play();
    }

    private List<Card> lastMoveCards = new ArrayList<>(); // Lưu các lá bài được đánh gần nhất
    private int currentPlayerIndex = 0; // Người chơi bắt đầu lượt (người đầu tiên)

    private void startTurn(Stage primaryStage, List<Player> players, Pane gamePane) {
        // Đảm bảo có người chơi
        if (players == null || players.isEmpty()) {
            System.out.println("Không có người chơi!");
            return;
        }

        // Kiểm tra điều kiện thắng
        checkForWinner(players);

        // Lấy người chơi hiện tại
        Player currentPlayer = players.get(currentPlayerIndex);

        // Hiển thị thông báo lượt chơi
        showPlayerTurnMessage(gamePane, currentPlayer.getName());

        // Xử lý lượt chơi của AI nếu là AI Player
        if (currentPlayer instanceof AI_Player) {
            handleAITurn((AI_Player) currentPlayer, gamePane);
            return;
        }

        // Hiển thị bài của người chơi hiện tại
        showPlayerHand(gamePane, currentPlayer);

        // Tạo nút ĐÁNH và BỎ LƯỢT
        Button playButton = new Button("ĐÁNH");
        Button skipButton = new Button("BỎ LƯỢT");
        playButton.setLayoutX(150);
        playButton.setLayoutY(300);
        skipButton.setLayoutX(250);
        skipButton.setLayoutY(300);
        // Đặt style để dễ nhìn
        playButton.setStyle("-fx-background-color: green; -fx-text-fill: black;");
        skipButton.setStyle("-fx-background-color: red; -fx-text-fill: black;");

        gamePane.getChildren().addAll(playButton, skipButton);

        System.out.println("Lượt của: " + currentPlayer.getName());
        System.out.println("Hiển thị nút: ĐÁNH và BỎ LƯỢT.");

        // Xử lý sự kiện ĐÁNH
        playButton.setOnAction(e -> {
            List<Card> selectedCards = currentPlayer.getSelectedCards();

            // Kiểm tra tính hợp lệ của nước đi
            if (selectedCards.isEmpty()) {
                showWarning("Vui lòng chọn bài để đánh!");
                return;
            }

            if (!GameLogic.isValidMove(selectedCards)) {
                showWarning("Nước đi không hợp lệ!");
                return;
            }

            // Nếu không phải lượt đầu tiên, kiểm tra có thể đánh được không
            if (!lastMoveCards.isEmpty() && !GameLogic.canBeatPreviousMove(lastMoveCards, selectedCards)) {
                showWarning("Bạn không thể đánh những lá bài này!");
                return;
            }

            // Hiển thị bài đánh ra
            displayPlayedCards(gamePane, selectedCards);

            // Cập nhật last move
            lastMoveCards = selectedCards;

            // Xóa bài khỏi tay người chơi
            currentPlayer.removeCards(selectedCards);

            // Xóa các nút và bài trên tay
            gamePane.getChildren().removeAll(playButton, skipButton);
            gamePane.getChildren().removeIf(node -> node.getId() != null && node.getId().equals("playerCard"));

            // Chuyển lượt
            currentPlayerIndex = (currentPlayerIndex + 1) % players.size();

            // Bắt đầu lượt mới
            startTurn(primaryStage, players, gamePane);
        });

        // Xử lý sự kiện BỎ LƯỢT
        skipButton.setOnAction(e -> {
            // Xóa các nút và bài trên tay
            gamePane.getChildren().removeAll(playButton, skipButton);
            gamePane.getChildren().removeIf(node -> node.getId() != null && node.getId().equals("playerCard"));

            // Chuyển lượt
            currentPlayerIndex = (currentPlayerIndex + 1) % players.size();

            // Bắt đầu lượt mới
            startTurn(primaryStage, players, gamePane);
        });
    }

    private void handleAITurn(AI_Player aiPlayer, Pane gamePane) {
        // Chọn nước đi của AI
        List<Card> aiMove = aiPlayer.chooseMove(lastMoveCards, new GameLogic());

        // Nếu AI có thể đánh bài
        if (aiMove != null && !aiMove.isEmpty()) {
            // Hiển thị bài AI đánh
            displayPlayedCards(gamePane, aiMove);

            // Cập nhật last move
            lastMoveCards = aiMove;

            // Xóa bài khỏi tay AI
            aiPlayer.removeCards(aiMove);
        } else {
            // AI bỏ lượt
            System.out.println(aiPlayer.getName() + " bỏ lượt.");
        }

        // Chuyển lượt
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();

        // Bắt đầu lượt mới
        startTurn(null, players, gamePane);
    }

    private void showPlayerHand(Pane gamePane, Player player) {
        List<Card> hand = player.getHand();
        double startX = 200;
        double startY = 600;

        for (int i = 0; i < hand.size(); i++) {
            Card card = hand.get(i);
            String cardImagePath = "/anh_bai/" + card.getRank().toLowerCase() + "_" + card.getSuit().toLowerCase() + ".png";
            ImageView cardView = new ImageView(new Image(getClass().getResourceAsStream(cardImagePath)));
            cardView.setFitWidth(100);
            cardView.setFitHeight(150);

            // Đặt vị trí xếp chồng lên nhau
            cardView.setLayoutX(startX + i * 40); // Cách nhau 40px
            cardView.setLayoutY(startY);

            // Đặt ID để dễ dàng xóa sau này
            cardView.setId("playerCard");

            // Thêm hiệu ứng khi được chọn
            cardView.setOnMouseClicked(event -> {
                if (cardView.getLayoutY() == startY) {
                    cardView.setLayoutY(startY - 20); // Nhô cao lên
                    player.selectCard(card);
                } else {
                    cardView.setLayoutY(startY); // Trở về vị trí cũ
                    player.deselectCard(card);
                }
            });

            gamePane.getChildren().add(cardView);
        }
    }

    private void displayPlayedCards(Pane gamePane, List<Card> cards) {
        // Xóa bài cũ của người chơi trước
        gamePane.getChildren().removeIf(node -> node.getId() != null && node.getId().equals("playedCard"));

        // Xác định vị trí bắt đầu để trải ngang bài
        double startX = 300; // Vị trí x ngang giữa màn hình
        double startY = 250; // Vị trí y giữa màn hình

        for (int i = 0; i < cards.size(); i++) {
            Card card = cards.get(i);
            String cardImagePath = "/anh_bai/" + card.getRank().toLowerCase() + "_" + card.getSuit().toLowerCase() + ".png";

            // Tạo hình ảnh lá bài
            ImageView cardView = new ImageView(new Image(getClass().getResourceAsStream(cardImagePath)));
            cardView.setFitWidth(100);
            cardView.setFitHeight(150);

            // Đặt vị trí xếp ngang
            cardView.setLayoutX(startX + i * 50); // Khoảng cách giữa các lá bài là 50px
            cardView.setLayoutY(startY);
            // Đặt ID để nhận diện (giúp xóa bài cũ)
            cardView.setId("playedCard");

            // Thêm hình ảnh lá bài vào gamePane
            gamePane.getChildren().add(cardView);
        }
    }

    private void checkForWinner(List<Player> players) {
        for (Player player : players) {
            if (player.getCards().isEmpty()) { // Nếu người chơi hết bài
                showWinner(player.getName());
                return;
            }
        }
    }

    private void showWinner(String playerName) {
        // Tạo Alert để hiển thị người chiến thắng
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Kết Thúc Trò Chơi");
        alert.setHeaderText("Người chiến thắng!");
        alert.setContentText(playerName + " đã chiến thắng!");

        // Thêm nút để đóng game hoặc chơi lại
        ButtonType playAgainButton = new ButtonType("Chơi Lại", ButtonBar.ButtonData.OK_DONE);
        ButtonType exitButton = new ButtonType("Thoát", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(playAgainButton, exitButton);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent()) {
            if (result.get() == playAgainButton) {
                // Logic để chơi lại game
                restart();
            } else {
                // Thoát game
                System.exit(0);
            }
        }
    }

    private void restart() {
        // Tạo lại stage ban đầu
        Stage primaryStage = new Stage();
        start(primaryStage);
    }

    // Các phương thức hỗ trợ khác
    private void showPlayerTurnMessage(Pane gamePane, String playerName) {
        // Xóa thông báo cũ nếu có
        gamePane.getChildren().removeIf(node -> node.getId() != null && node.getId().equals("turnMessage"));

        Label turnLabel = new Label("Lượt của " + playerName);
        turnLabel.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        turnLabel.setTextFill(Color.WHITE);
        turnLabel.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5); -fx-padding: 10px;");
        turnLabel.setLayoutX(300);
        turnLabel.setLayoutY(50);

        // Đặt ID để dễ dàng xóa
        turnLabel.setId("turnMessage");

        gamePane.getChildren().add(turnLabel);

        // Tự động xóa thông báo sau 2 giây
        Timeline timeline = new Timeline(new KeyFrame(
                Duration.seconds(2),
                event -> gamePane.getChildren().remove(turnLabel)
        ));
        timeline.setCycleCount(1);
        timeline.play();
    }

    private void showWarning(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Cảnh Báo");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}



