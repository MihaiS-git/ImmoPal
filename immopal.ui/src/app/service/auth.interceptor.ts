import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { AuthService } from "./auth.service";
import { Observable } from "rxjs";

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor(private authService: AuthService) {}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const token = this.authService.getToken();
    if (token) {
      const cleanedToken = token.replace(/"/g, ''); // Remove all occurrences of "
      const cloned = req.clone({
        setHeaders: {
          Authorization: `Bearer ${cleanedToken}`
        }
      });
      return next.handle(cloned);
    } else {
      return next.handle(req);
    }
  }
}
