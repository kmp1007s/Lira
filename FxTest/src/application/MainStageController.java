package application;

import java.util.List;
import com.dgsw.tcpcom.TcpClient;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class MainStageController {

	public MainStageController() {

		super();
	}

	@FXML
	private Label label1;

	@FXML
	private TextField lineValue1;
	@FXML
	private TextField lineValue2;
	@FXML
	private TextField lineValue3;

	private TcpClient client = new TcpClient("10.80.163.159", 5200);

	public DropShadow getDefaultDropShadow() {

		DropShadow dropShadow = new DropShadow();

		dropShadow.setBlurType(BlurType.GAUSSIAN);

		dropShadow.setColor(Color.LIGHTGRAY);

		dropShadow.setHeight(20);

		dropShadow.setWidth(20);

		dropShadow.setRadius(10);

		dropShadow.setOffsetX(0);
		dropShadow.setOffsetY(0);

		return dropShadow;
	}

	/** 서버로 전송 시 라인에 null값이 들어가는 것을 방지
	 * 
	 * @param lineList 줄 객체의 리스트
	 */
	public void preventNullInLine(List<TextField> lineList) {

		for (TextField line : lineList) {

			if (line.getText() == null) {

				System.out.println("line: " + line);
				line.setText("1");
			}
		}
	}

	/** 터치패드에 입력된 좌표를 로그로 남김
	 * 
	 * @param event 마우스이벤트 객체
	 */
	public void logPosition(MouseEvent event) {

		System.out.println("X좌표 :" + event.getX());
		System.out.println("Y좌표 :" + event.getY());
	}

	/** 터치패드의 크기를 로그로 남김
	 * 
	 */
	public void logTouchPadSize() {

		System.out.println("width: " + label1.getWidth());
		System.out.println("height: " + label1.getHeight());
	}

	/** 각 줄의 값을 로그로 남김
	 * 
	 */
	public void logLineValue() {

		System.out.println("Line1:" + lineValue1.getText());
	}

	/** 전체적인 로그 작업
	 * 
	 * @param event 마우스이벤트 객체
	 */
	public void logAll(MouseEvent event) {

		logTouchPadSize();
		logPosition(event);
		logLineValue();
	}

	/** 문자열 전송에 사용할 포맷을 만듦
	 * 
	 * @param event
	 * @return 문자열 전송에 사용할 포맷
	 */
	public String makeFormat(MouseEvent event) {

		String sendFormat = "%s_%s_%s";

		String sendData = String.format(sendFormat,

		       lineValue1.getText(), event.getX() + "", event.getY() + "");

		return sendData;
	}

	/** 서버로부터 받을 데이터를 로그로 남긴다
	 * @param willReceived 서버로부터 받을 데이터
	 */
	public void logDataFromServer(String[] willReceived) {
		
		System.out.println("---------------------");

		System.out.println("서버로부터 받을 X: " + willReceived[1]);
		System.out.println("서버로부터 받을 Y: " + willReceived[2]);

		System.out.println("서버로부터 받을 Line1: " + willReceived[0]);
	}
	
	/** 서버로부터 받은 데이터가 제대로 된 양식을 갖추고 있는지 검사
	 * @param willReceived 서버로부터 받을 데이터
	 * @return 제대로 -> true / 양식에 부합하지 않음 -> false
	 */
	public boolean isRightDataFromServer(String[] willReceived) {
		
		if (willReceived != null && willReceived.length == 3) {

			return true;
			
		} else {
			
			return false;	
		}
	}
	
	/** 서버로부터 정상적인 데이터를 받았을 경우의 데이터를 분해해서 출력한다
	 * @param sendData 서버에서 정상적으로 데이터를 받았을 경우의 데이터
	 */
	public void checkDataWillReceived(String sendData) {

		//_를 기준으로 문자열 분해
		String[] willReceived = sendData.split("_");

		if (isRightDataFromServer(willReceived)) {

			logDataFromServer(willReceived);
		}
	}

	
	/** 서버로 데이터를 전송한다
	 * @param event 마우스이벤트 객체
	 */
	public void sendToServer(MouseEvent event) {

		String sendData = makeFormat(event);

		System.out.println("서버에 보낸 데이터 : " + sendData);

		checkDataWillReceived(sendData);

		client.ClientRun(sendData);
	}

	@FXML
	private void initialize() {

		label1.setEffect(getDefaultDropShadow());

		label1.setText("TOUCH PAD");

		label1.setOnMousePressed(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {

//				List<TextField> lineList = new ArrayList<TextField>();
//
//				lineList.add(lineValue1);
//				lineList.add(lineValue2);
//				lineList.add(lineValue3);
//
//				preventNullInLine(lineList);

				logAll(event);

				sendToServer(event);
			}
		});
	}
}
