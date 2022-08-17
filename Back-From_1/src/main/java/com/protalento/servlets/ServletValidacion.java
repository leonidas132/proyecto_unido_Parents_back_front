package com.protalento.servlets;

import com.protalento.entidadJdbc.User;
import com.protalento.enumerados.Alertas;
import com.protalento.implementacion.ImplUsuario;
import com.protalento.interfaces.Constantes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet( "/ServletValidacion")
public class ServletValidacion extends HttpServlet {
    private static final long serialVersionUID =1L;
    private static Logger logger = LogManager.getLogger();
    private ImplUsuario implUsuario;

// User232@mail.com' clave: User2234'
    public ServletValidacion() {
        super();
        implUsuario = new ImplUsuario();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String correo = request.getParameter("correo");
        String  clave = request.getParameter("clave");
        String  pagina= "login.jsp";
        RequestDispatcher requestDispatcher;

        /*Proporciona una manera de identificar a un usuario en más de una solicitud de página o
          visita a un sitio web y almacenar información sobre ese usuario.
          El contenedor de servlets utiliza esta interfaz para crear una sesión entre un cliente HTTP
          y un servidor HTTP. La sesión persiste durante un período de tiempo específico,
          a través de más de una conexión o solicitud de página  */
        HttpSession sesion = null;
        User user = implUsuario.buscarPorCorreoClave(correo,clave);

         // se validan los intentos fallidos y si superan el rango se generara una alerta
        // de lo contrario se creara una sesion
        if(user != null){
            if (user.getIntentosFallidos()>= Constantes.MAXIMO_INTENTOS_FALLIDOS){
                //alerta usuario bloqueado
                request.setAttribute("Alertas", Alertas.USUARIO_BLOQUEADO);
                logger.warn(user +" "+ Alertas.USUARIO_BLOQUEADO);
            }else if(!user.getClave().equals(clave)){
                request.setAttribute("Alerta",Alertas.CREDENCIALES_INCORRECTAS);
                logger.warn(user +" "+ Alertas.CREDENCIALES_INCORRECTAS);
            }else{
                sesion= request.getSession();
                sesion.setAttribute("usuario",user);
                pagina = "index.jsp";
            }

        }else{
            // si el usuario esta en null las credenciales osn incorrectas
            request.setAttribute("Alertas", Alertas.CREDENCIALES_INCORRECTAS);
            logger.warn(correo +" "+ Alertas.CREDENCIALES_INCORRECTAS);

        }
        //solo podra ingresar a la pagina index una vez se cumpla la condicion de lo contrario seguira en el login
        requestDispatcher = request.getRequestDispatcher(pagina);
        requestDispatcher.forward(request,response);



    }
}
