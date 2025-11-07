package dev.jimmycedeno.aidoc;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.core.io.Resource;
import org.springframework.shell.command.annotation.Command;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Command
@RequiredArgsConstructor
public class SpringAssistantCommand {
  private final ChatModel chatModel;
  private final VectorStore vectorStore;

  @Value("classpath:/prompts/el-humilde-programador.st")
  private Resource sbPromptTemplate;

  @Command(command = "q")
  public String question(@DefaultValue(value = "Que es un programador humilde") String message) {
    PromptTemplate promptTemplate = new PromptTemplate(sbPromptTemplate);
    Map<String, Object> promptParameters = new HashMap<>();
    promptParameters.put("input", message);
    promptParameters.put("documents", String.join("\n", findSimiliarDocument(message)));

    return chatModel.call(promptTemplate.create(promptParameters))
        .getResult()
        .getOutput()
        .getText();
  }

  private List<String> findSimiliarDocument(String message) {
    List<Document> similarDocuments = vectorStore.similaritySearch(SearchRequest.builder().query(message).topK(3).build());
    return similarDocuments.stream().map(Document::getText).toList();
  }
}
