import { Route } from '@angular/router';

import { UserRouteAccessService } from '../shared';
import {HomeVisitorComponent} from "./home-visitor.component";

export const HOME_ROUTE: Route = {
    path: '',
    component: HomeVisitorComponent,
    data: {
        authorities: [],
        pageTitle: 'home.title'
    }
};
