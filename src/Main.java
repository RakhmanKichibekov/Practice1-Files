import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.Enumeration;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Выберите операцию:");
            System.out.println("1. Вывести информацию о дисках");
            System.out.println("2. Работа с текстовыми файлами");
            System.out.println("3. Работа с JSON файлами");
            System.out.println("4. Работа с XML файлами");
            System.out.println("5. Работа с ZIP архивами");
            System.out.println("6. Выход");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    displayDiskInfo();
                    break;
                case 2:
                    handleTextFiles();
                    break;
                case 3:
                    handleJSONFiles();
                    break;
                case 4:
                    handleXMLFiles();
                    break;
                case 5:
                    handleZipArchives();
                    break;
                case 6:
                    System.out.println("Выход из программы.");
                    System.exit(0);
                default:
                    System.out.println("Некорректный выбор. Попробуйте снова.");
            }
        }
    }

    // Вывод информации о дисках
    private static void displayDiskInfo() {
        File[] roots = File.listRoots();
        for (File root : roots) {
            System.out.println("Диск: " + root);
            System.out.println("Метка: " + root.getName());
            System.out.println("Размер: " + root.getTotalSpace() / (1024 * 1024) + " MB");
            System.out.println("Тип файловой системы: " + root.getTotalSpace());
        }
    }

    // Работа с текстовыми файлами
    private static void handleTextFiles() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите имя файла:");
        String fileName = scanner.next();

        File file = new File(fileName);

        System.out.println("Выберите операцию:");
        System.out.println("1. Создать файл");
        System.out.println("2. Записать в файл");
        System.out.println("3. Прочитать файл");
        System.out.println("4. Удалить файл");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                try {
                    if (file.createNewFile()) {
                        System.out.println("Файл создан успешно.");
                    } else {
                        System.out.println("Файл уже существует.");
                    }
                } catch (IOException e) {
                    System.out.println("Ошибка при создании файла: " + e.getMessage());
                }
                break;
            case 2:
                try (FileWriter writer = new FileWriter(file)) {
                    System.out.println("Введите текст для записи в файл (для завершения введите 'exit'):");
                    while (true) {
                        String line = scanner.next();
                        if (line.equals("exit")) {
                            break;
                        }
                        writer.write(line + "\n");
                    }
                    System.out.println("Текст успешно записан в файл.");
                } catch (IOException e) {
                    System.out.println("Ошибка при записи в файл: " + e.getMessage());
                }
                break;
            case 3:
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.out.println(line);
                    }
                } catch (IOException e) {
                    System.out.println("Ошибка при чтении файла: " + e.getMessage());
                }
                break;
            case 4:
                if (file.delete()) {
                    System.out.println("Файл удален.");
                } else {
                    System.out.println("Не удалось удалить файл.");
                }
                break;
            default:
                System.out.println("Некорректный выбор операции.");
        }
    }

    // Работа с JSON файлами
    private static void handleJSONFiles() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите имя JSON файла:");
        String fileName = scanner.next();

        System.out.println("Выберите операцию:");
        System.out.println("1. Создать JSON файл");
        System.out.println("2. Записать в JSON файл");
        System.out.println("3. Прочитать JSON файл");
        System.out.println("4. Удалить JSON файл");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                createJSONFile(fileName);
                break;
            case 2:
                writeJSONFile(fileName);
                break;
            case 3:
                readJSONFile(fileName);
                break;
            case 4:
                deleteFile(fileName);
                break;
            default:
                System.out.println("Некорректный выбор операции.");
        }
    }

    // Создание JSON файла
    private static void createJSONFile(String fileName) {
        JSONObject jsonObject = new JSONObject();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите данные в формате ключ-значение (для завершения введите 'exit'):");

        while (true) {
            System.out.print("Ключ: ");
            String key = scanner.next();
            if (key.equals("exit")) {
                break;
            }
            System.out.print("Значение: ");
            String value = scanner.next();
            jsonObject.put(key, value);
        }

        try (FileWriter fileWriter = new FileWriter(fileName)) {
            fileWriter.write(jsonObject.toJSONString());
            System.out.println("JSON файл успешно создан.");
        } catch (IOException e) {
            System.out.println("Ошибка при создании JSON файла: " + e.getMessage());
        }
    }

    // Запись в JSON файл
    private static void writeJSONFile(String fileName) {
        JSONObject jsonObject = new JSONObject();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите данные в формате ключ-значение (для завершения введите 'exit'):");

        while (true) {
            System.out.print("Ключ: ");
            String key = scanner.next();
            if (key.equals("exit")) {
                break;
            }
            System.out.print("Значение: ");
            String value = scanner.next();
            jsonObject.put(key, value);
        }

        try (FileWriter fileWriter = new FileWriter(fileName)) {
            fileWriter.write(jsonObject.toJSONString());
            System.out.println("Данные успешно записаны в JSON файл.");
        } catch (IOException e) {
            System.out.println("Ошибка при записи в JSON файл: " + e.getMessage());
        }
    }

    // Чтение JSON файла
    private static void readJSONFile(String fileName) {
        JSONParser jsonParser = new JSONParser();

        try {
            Object obj = jsonParser.parse(new FileReader(fileName));
            JSONObject jsonObject = (JSONObject) obj;
            System.out.println("Содержимое JSON файла:");
            for (Object key : jsonObject.keySet()) {
                System.out.println(key + ": " + jsonObject.get(key));
            }
        } catch (IOException | ParseException e) {
            System.out.println("Ошибка при чтении JSON файла: " + e.getMessage());
        }
    }

    // Работа с XML файлами
    private static void handleXMLFiles() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите имя XML файла:");
        String fileName = scanner.next();

        System.out.println("Выберите операцию:");
        System.out.println("1. Создать XML файл");
        System.out.println("2. Записать в XML файл");
        System.out.println("3. Прочитать XML файл");
        System.out.println("4. Удалить XML файл");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                createXMLFile(fileName);
                break;
            case 2:
                writeXMLFile(fileName);
                break;
            case 3:
                readXMLFile(fileName);
                break;
            case 4:
                deleteFile(fileName);
                break;
            default:
                System.out.println("Некорректный выбор операции.");
        }
    }

    // Создание XML файла
    private static void createXMLFile(String fileName) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("data");
            doc.appendChild(rootElement);

            Scanner scanner = new Scanner(System.in);
            System.out.println("Введите данные в формате ключ-значение (для завершения введите 'exit'):");

            while (true) {
                System.out.print("Ключ: ");
                String key = scanner.next();
                if (key.equals("exit")) {
                    break;
                }
                System.out.print("Значение: ");
                String value = scanner.next();
                Element element = doc.createElement(key);
                element.appendChild(doc.createTextNode(value));
                rootElement.appendChild(element);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(fileName));

            transformer.transform(source, result);

            System.out.println("XML файл успешно создан.");
        } catch (ParserConfigurationException | TransformerException e) {
            System.out.println("Ошибка при создании XML файла: " + e.getMessage());
        }
    }

    // Запись в XML файл
    private static void writeXMLFile(String fileName) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            Document doc = docBuilder.parse(fileName);

            Element rootElement = doc.getDocumentElement();

            Scanner scanner = new Scanner(System.in);
            System.out.println("Введите данные в формате ключ-значение (для завершения введите 'exit'):");

            while (true) {
                System.out.print("Ключ: ");
                String key = scanner.next();
                if (key.equals("exit")) {
                    break;
                }
                System.out.print("Значение: ");
                String value = scanner.next();
                Element element = doc.createElement(key);
                element.appendChild(doc.createTextNode(value));
                rootElement.appendChild(element);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(fileName));

            transformer.transform(source, result);

            System.out.println("Данные успешно записаны в XML файл.");
        } catch (ParserConfigurationException | TransformerException | IOException | SAXException e) {
            System.out.println("Ошибка при записи в XML файл: " + e.getMessage());
        }
    }

    // Чтение XML файла
    private static void readXMLFile(String fileName) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(fileName);

            Element rootElement = doc.getDocumentElement();
            NodeList nodes = rootElement.getChildNodes();

            System.out.println("Содержимое XML файла:");
            for (int i = 0; i < nodes.getLength(); i++) {
                Node node = nodes.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    System.out.println(node.getNodeName() + ": " + node.getTextContent());
                }
            }
        } catch (ParserConfigurationException | IOException | SAXException e) {
            System.out.println("Ошибка при чтении XML файла: " + e.getMessage());
        }
    }

    // Работа с ZIP архивами
    private static void handleZipArchives() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите имя ZIP архива:");
        String zipFileName = scanner.next();

        System.out.println("Выберите операцию:");
        System.out.println("1. Создать ZIP архив");
        System.out.println("2. Добавить файл в ZIP архив");
        System.out.println("3. Разархивировать ZIP архив");
        System.out.println("4. Удалить файл и ZIP архив");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                createZipArchive(zipFileName);
                break;
            case 2:
                System.out.println("Введите имя файла, который нужно добавить в ZIP архив:");
                String fileToAdd = scanner.next();
                addToZipArchive(zipFileName, fileToAdd);
                break;
            case 3:
                extractZipArchive(zipFileName);
                break;
            case 4:
                deleteFile(zipFileName);
                break;
            default:
                System.out.println("Некорректный выбор операции.");
        }
    }

    // Создание ZIP архива
    private static void createZipArchive(String zipFileName) {
        try {
            FileOutputStream fos = new FileOutputStream(zipFileName);
            ZipOutputStream zos = new ZipOutputStream(fos);
            zos.close();

            System.out.println("ZIP архив успешно создан.");
        } catch (IOException e) {
            System.out.println("Ошибка при создании ZIP архива: " + e.getMessage());
        }
    }

    // Добавление файла в ZIP архив
    private static void addToZipArchive(String zipFileName, String fileToAdd) {
        try {
            FileOutputStream fos = new FileOutputStream(zipFileName);
            ZipOutputStream zos = new ZipOutputStream(fos);
            FileInputStream fis = new FileInputStream(fileToAdd);

            ZipEntry zipEntry = new ZipEntry(fileToAdd);
            zos.putNextEntry(zipEntry);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) > 0) {
                zos.write(buffer, 0, length);
            }

            zos.closeEntry();
            fis.close();
            zos.close();

            System.out.println("Файл успешно добавлен в ZIP архив.");
        } catch (IOException e) {
            System.out.println("Ошибка при добавлении файла в ZIP архив: " + e.getMessage());
        }
    }

    // Разархивирование ZIP архива
    private static void extractZipArchive(String zipFileName) {
        try {
            ZipFile zipFile = new ZipFile(zipFileName);
            Enumeration<? extends ZipEntry> entries = zipFile.entries();

            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                InputStream is = zipFile.getInputStream(entry);

                System.out.println("Имя файла в ZIP архиве: " + entry.getName());
                FileOutputStream fos = new FileOutputStream(entry.getName());

                byte[] buffer = new byte[1024];
                int length;
                while ((length = is.read(buffer)) > 0) {
                    fos.write(buffer, 0, length);
                }

                fos.close();
                is.close();
            }

            zipFile.close();
            System.out.println("ZIP архив успешно разархивирован.");
        } catch (IOException e) {
            System.out.println("Ошибка при разархивировании ZIP архива: " + e.getMessage());
        }
    }

    // Удаление файла
    private static void deleteFile(String fileName) {
        File file = new File(fileName);
        if (file.exists() && file.delete()) {
            System.out.println("Файл успешно удален.");
        } else {
            System.out.println("Не удалось удалить файл.");
        }
    }
}
