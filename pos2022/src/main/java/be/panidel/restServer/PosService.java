package be.panidel.restServer;

import java.io.IOException;
import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import be.panidel.dao.DataFacade;
import be.panidel.exception.IllegalTypeException;
import be.panidel.model.ItemModel;
import be.panidel.model.ProductModels;
import be.panidel.model.SaleModel;
import be.panidel.model.ServiceMessage.ResponseResult;
import be.panidel.model.ServiceResponse;
import be.panidel.utils.XlsProductDataReaderHelper;

@Path("/pos")
public class PosService {

	private final static Logger LOG = LoggerFactory.getLogger(PosService.class);

	@GET
	@Path("/products")
	@Produces(MediaType.APPLICATION_JSON)
	public ProductModels getAllProduct() {

		String fileName = "POSproducts.xlsx";
		ProductModels products = null;

		try {

			LOG.info("get product");

			products = XlsProductDataReaderHelper.parseXlsxfile(fileName);

			LOG.info("return product");

		} catch (IOException | InvalidFormatException | IllegalTypeException e) {
			LOG.error("", e);
		}

		return products;
	}

	@POST
	@Path("/writeSale")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public ServiceResponse writeSale(SaleModel sale) {

		try {

			LOG.info("Receive message : " + sale);

			DataFacade.instance.createSale(sale);

			String message = "the message is success";
			ResponseResult responseResult = ResponseResult.SUCCESS;
			Date date = new Date();
			ServiceResponse serviceResponse = new ServiceResponse(message, sale.getId(), responseResult, date);
			LOG.info("return message : " + serviceResponse);

			return serviceResponse;

		} catch (Throwable e) {

			LOG.error("" + sale, e);

			String message = "the message is failed";
			ResponseResult responseResult = ResponseResult.FAILED;
			Date date = new Date();

			ServiceResponse serviceResponse = new ServiceResponse(message, null, responseResult, date);

			return serviceResponse;
		}
	}

	@POST
	@Path("/getSale")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public ServiceResponse getSale(SaleModel sale) {

		try {

			LOG.info("Receive message : " + sale);

			SaleModel saleModelRetValue = DataFacade.instance.getSale(sale);
			LOG.info("" + saleModelRetValue);

			String message = "the message is success";
			ResponseResult responseResult = ResponseResult.SUCCESS;
			Date date = new Date();
			ServiceResponse serviceResponse = new ServiceResponse(message, sale.getId(), responseResult, date);
			LOG.info("return message : " + serviceResponse);

			return serviceResponse;

		} catch (Throwable e) {

			LOG.error("" + sale, e);

			String message = "the message is failed";
			ResponseResult responseResult = ResponseResult.FAILED;
			Date date = new Date();

			ServiceResponse serviceResponse = new ServiceResponse(message, null, responseResult, date);

			return serviceResponse;
		}
	}

	@POST
	@Path("/writeItem")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public ServiceResponse writeItem(ItemModel itemModel) {

		try {

			LOG.info("Receive message : " + itemModel);

			String message = "the message is success";
			ResponseResult responseResult = ResponseResult.SUCCESS;
			Date date = new Date();

			ServiceResponse serviceResponse = new ServiceResponse(message, null, responseResult, date);

			LOG.info("return message : " + serviceResponse);

			return serviceResponse;

		} catch (Throwable e) {

			LOG.error("" + itemModel, e);

			String message = "the message is failed";
			ResponseResult responseResult = ResponseResult.FAILED;
			Date date = new Date();

			ServiceResponse serviceResponse = new ServiceResponse(message, null, responseResult, date);

			return serviceResponse;
		}
	}
}
