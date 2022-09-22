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

	@Test
	void preparacaoAmbienteTest() {
		assertThat(pacienteControllerAPI).isNotNull();
		assertThat(mockMvc).isNotNull();
	}

	@Test
	void pacienteControllerAPIPOSTGETTest() throws Exception {
		String jwtToken = getToken();

		MvcResult result = mockMvc.perform(post("/api/v1/pacientes")
				.content("{\"nome\":\"Zezinho\",\"sexo\":\"Masculino\"}")
				.header("Authorization", "Bearer " + jwtToken)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andReturn();

		String resultStr = result.getResponse().getContentAsString();

		JSONObject objJson = new JSONObject(resultStr);

		mockMvc.perform(get("/api/v1/pacientes/" + objJson.getString("id"))
				.header("Authorization", "Bearer " + jwtToken))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.nome", is("Zezinho")))
				.andExpect(jsonPath("$.sexo", is("Masculino")));
	}

	@Test
	void medicoControllerAPIPOSTGETTest() throws Exception {
		String jwtToken = getToken();

		MvcResult result = mockMvc.perform(post("/api/v1/medicos")
				.content("{\"nome\":\"Lucas\",\"crm\":\"123\"}")
				.header("Authorization", "Bearer " + jwtToken)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andReturn();

		String resultStr = result.getResponse().getContentAsString();
		JSONObject objJson = new JSONObject(resultStr);

		mockMvc.perform(get("/api/v1/medicos/" + objJson.getString("id"))
				.header("Authorization", "Bearer " + jwtToken))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.nome", is("Lucas")))
				.andExpect(jsonPath("$.crm", is("123")));
	}

	@Test
	void procedimentoAPIPOSTGETTest() throws Exception {
		String jwtToken = getToken();

		MvcResult result = mockMvc.perform(post("/api/v1/procedimentos")
				.content("{\"descricao\":\"Cirurgia\"}")
				.header("Authorization", "Bearer " + jwtToken)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andReturn();

		String resultStr = result.getResponse().getContentAsString();
		JSONObject objJson = new JSONObject(resultStr);

		mockMvc.perform(get("/api/v1/procedimentos/" + objJson.getString("id"))
				.header("Authorization", "Bearer " + jwtToken))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.descricao", is("Cirurgia")));

	}

	private String getToken() throws Exception {

		MvcResult resultAuth = mockMvc.perform(post("/api/v1/auth/signin")
				.content("{\"usuario\":\"admin\",\"senha\":\"admin\"}")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();

		String jwtToken = resultAuth.getResponse().getContentAsString();

		return jwtToken;
	}

}
