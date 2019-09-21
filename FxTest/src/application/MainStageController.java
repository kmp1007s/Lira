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

	/** ������ ���� �� ���ο� null���� ���� ���� ����
	 * 
	 * @param lineList �� ��ü�� ����Ʈ
	 */
	public void preventNullInLine(List<TextField> lineList) {

		for (TextField line : lineList) {

			if (line.getText() == null) {

				System.out.println("line: " + line);
				line.setText("1");
			}
		}
	}

	/** ��ġ�е忡 �Էµ� ��ǥ�� �α׷� ����
	 * 
	 * @param event ���콺�̺�Ʈ ��ü
	 */
	public void logPosition(MouseEvent event) {

		System.out.println("X��ǥ :" + event.getX());
		System.out.println("Y��ǥ :" + event.getY());
	}

	/** ��ġ�е��� ũ�⸦ �α׷� ����
	 * 
	 */
	public void logTouchPadSize() {

		System.out.println("width: " + label1.getWidth());
		System.out.println("height: " + label1.getHeight());
	}

	/** �� ���� ���� �α׷� ����
	 * 
	 */
	public void logLineValue() {

		System.out.println("Line1:" + lineValue1.getText());
	}

	/** ��ü���� �α� �۾�
	 * 
	 * @param event ���콺�̺�Ʈ ��ü
	 */
	public void logAll(MouseEvent event) {

		logTouchPadSize();
		logPosition(event);
		logLineValue();
	}

	/** ���ڿ� ���ۿ� ����� ������ ����
	 * 
	 * @param event
	 * @return ���ڿ� ���ۿ� ����� ����
	 */
	public String makeFormat(MouseEvent event) {

		String sendFormat = "%s_%s_%s";

		String sendData = String.format(sendFormat,

		       lineValue1.getText(), event.getX() + "", event.getY() + "");

		return sendData;
	}

	/** �����κ��� ���� �����͸� �α׷� �����
	 * @param willReceived �����κ��� ���� ������
	 */
	public void logDataFromServer(String[] willReceived) {
		
		System.out.println("---------------------");

		System.out.println("�����κ��� ���� X: " + willReceived[1]);
		System.out.println("�����κ��� ���� Y: " + willReceived[2]);

		System.out.println("�����κ��� ���� Line1: " + willReceived[0]);
	}
	
	/** �����κ��� ���� �����Ͱ� ����� �� ����� ���߰� �ִ��� �˻�
	 * @param willReceived �����κ��� ���� ������
	 * @return ����� -> true / ��Ŀ� �������� ���� -> false
	 */
	public boolean isRightDataFromServer(String[] willReceived) {
		
		if (willReceived != null && willReceived.length == 3) {

			return true;
			
		} else {
			
			return false;	
		}
	}
	
	/** �����κ��� �������� �����͸� �޾��� ����� �����͸� �����ؼ� ����Ѵ�
	 * @param sendData �������� ���������� �����͸� �޾��� ����� ������
	 */
	public void checkDataWillReceived(String sendData) {

		//_�� �������� ���ڿ� ����
		String[] willReceived = sendData.split("_");

		if (isRightDataFromServer(willReceived)) {

			logDataFromServer(willReceived);
		}
	}

	
	/** ������ �����͸� �����Ѵ�
	 * @param event ���콺�̺�Ʈ ��ü
	 */
	public void sendToServer(MouseEvent event) {

		String sendData = makeFormat(event);

		System.out.println("������ ���� ������ : " + sendData);

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
