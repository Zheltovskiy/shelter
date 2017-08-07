import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ShelterComponent } from './shelter.component';
import { ShelterDetailComponent } from './shelter-detail.component';
import { ShelterPopupComponent } from './shelter-dialog.component';
import { ShelterDeletePopupComponent } from './shelter-delete-dialog.component';

import { Principal } from '../../shared';

export const shelterRoute: Routes = [
    {
        path: 'shelter',
        component: ShelterComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'shelterApp.shelter.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'shelter/:id',
        component: ShelterDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'shelterApp.shelter.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const shelterPopupRoute: Routes = [
    {
        path: 'shelter-new',
        component: ShelterPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'shelterApp.shelter.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'shelter/:id/edit',
        component: ShelterPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'shelterApp.shelter.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'shelter/:id/delete',
        component: ShelterDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'shelterApp.shelter.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
