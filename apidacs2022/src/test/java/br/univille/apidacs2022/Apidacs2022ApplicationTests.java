package br.univille.apidacs2022;

import static org.assertj.core.api.Assertions.assertThat;
// import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.hamcrest.Matchers.is;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import br.univille.apidacs2022.api.PacienteControllerAPI;

@SpringBootTest
@AutoConfigureMockMvc
class Apidacs2022ApplicationTests {

	@Autowired
	private PacienteControllerAPI pacienteControllerAPI;
	@Autowired
	private MockMvc mockMvc;
	private String jwtToken;

	@Test
	void preparacaoAmbienteTest() {
		assertThat(pacienteControllerAPI).isNotNull();
		assertThat(mockMvc).isNotNull();
	}

	private void geraToken() throws Exception {

		MvcResult resultAuth = mockMvc.perform(post("/api/v1/auth/signin")
				.content("{\"usuario\":\"admin\",\"senha\":\"admin\"}")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();

		jwtToken = resultAuth.getResponse().getContentAsString();
	}

	@Test
	void pacienteControllerAPIPOSTGETTest() throws Exception {
		String resultStr;
		JSONObject objJson;
		MvcResult result;
		
		if (jwtToken == null) {
			geraToken();
		}

		result = mockMvc.perform(post("/api/v1/pacientes")
				.content("{\"nome\":\"Zezinho\",\"sexo\":\"Masculino\"}")
				.header("Authorization", "Bearer " + jwtToken)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andReturn();

		resultStr = result.getResponse().getContentAsString();
		objJson = new JSONObject(resultStr);

		mockMvc.perform(get("/api/v1/pacientes/" + objJson.getString("id"))
				.header("Authorization", "Bearer " + jwtToken))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.nome", is("Zezinho")))
				.andExpect(jsonPath("$.sexo", is("Masculino")));
	}

	@Test
	void medicoControllerAPIPOSTGETTest() throws Exception {
		MvcResult result;
		String resultStr;
		JSONObject objJson;
		
		if (jwtToken == null) {
			geraToken();
		}

		result = mockMvc.perform(post("/api/v1/medicos")
				.content("{\"nome\":\"Lucas\",\"crm\":\"123\"}")
				.header("Authorization", "Bearer " + jwtToken)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andReturn();

		resultStr = result.getResponse().getContentAsString();
		objJson = new JSONObject(resultStr);

		mockMvc.perform(get("/api/v1/medicos/" + objJson.getString("id"))
				.header("Authorization", "Bearer " + jwtToken))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.nome", is("Lucas")))
				.andExpect(jsonPath("$.crm", is("123")));
	}

	@Test
	void procedimentoAPIPOSTGETTest() throws Exception {
		String resultStr;
		JSONObject objJson;
		MvcResult result;
		
		if (jwtToken == null) {
			geraToken();
		}

		result = mockMvc.perform(post("/api/v1/procedimentos")
				.content("{\"descricao\":\"Cirurgia\"}")
				.header("Authorization", "Bearer " + jwtToken)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andReturn();

		resultStr = result.getResponse().getContentAsString();
		objJson = new JSONObject(resultStr);

		mockMvc.perform(get("/api/v1/procedimentos/" + objJson.getString("id"))
				.header("Authorization", "Bearer " + jwtToken))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.descricao", is("Cirurgia")));

	}

	@Test
	void cidadeAPIPOSTGETTest() throws Exception {
		String resultStr;
		JSONObject objJson;
		MvcResult result;
		
		if (jwtToken == null) {
			geraToken();
		}

		result = mockMvc.perform(post("/api/v1/cidades")
		.content("{\"nome\":\"Joinville\"}")
		.header("Authorization", "Bearer " + jwtToken)
		.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isCreated()).andReturn();

		resultStr = result.getResponse().getContentAsString();
		objJson = new JSONObject(resultStr);

		mockMvc.perform(get("/api/v1/cidades/" + objJson.getString("id"))
		.header("Authorization", "Bearer " + jwtToken))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.nome", is("Joinville")));
		
	}

	@Test
	void procedimentoRealizadoAPIPOSTGETTest() throws Exception {
		String idProcedimento, json, descricaoProcedimentoRealizado, descricaoProcedimento, resultStr;
		JSONObject objJson;
		MvcResult result;

		if (jwtToken == null) {
			geraToken();
		}

		descricaoProcedimentoRealizado = "Procedimento de teste";
		descricaoProcedimento = "Procedimento de teste";
		idProcedimento = criaProcedimento(descricaoProcedimento);

		json = "{ ";
		json += 	" \"descricao\": \"" + descricaoProcedimentoRealizado + "\", ";
		json += 	" \"valor\": \"999.99\", ";
		json += 	" \"procedimento\": { ";
		json += 							" \"id\":\"" + idProcedimento + "\",";
		json += 							" \"descricao\":\"" + descricaoProcedimento + "\"";
		json += 	                  " } ";
		json += "}";

		result = mockMvc.perform(post("/api/v1/procedimentos/realizados")
				.content(json).header("Authorization", "Bearer " + jwtToken)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andReturn();

		resultStr = result.getResponse().getContentAsString();
		objJson = new JSONObject(resultStr);

		mockMvc.perform(get("/api/v1/procedimentos/realizados/" + objJson.getString("id"))
				.header("Authorization", "Bearer " + jwtToken))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.descricao", is(descricaoProcedimentoRealizado)))
				.andExpect(jsonPath("$.valor", is(999.99)));

	}

	private String criaProcedimento(String descricao) throws Exception {
		String idProcedimento, resultStr;
		JSONObject objJson;
		MvcResult result;

		result = mockMvc.perform(post("/api/v1/procedimentos")
				.content("{\"descricao\":\"" + descricao + "\"}")
				.header("Authorization", "Bearer " + jwtToken)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andReturn();

		resultStr = result.getResponse().getContentAsString();
		objJson = new JSONObject(resultStr);
		idProcedimento = objJson.getString("id");

		mockMvc.perform(get("/api/v1/procedimentos/" + objJson.getString("id"))
				.header("Authorization", "Bearer " + jwtToken))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.descricao", is(descricao)));

		return idProcedimento;
	}

}
