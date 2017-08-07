import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { PetComponent } from './pet.component';
import { PetDetailComponent } from './pet-detail.component';
import { PetPopupComponent } from './pet-dialog.component';
import { PetDeletePopupComponent } from './pet-delete-dialog.component';

import { Principal } from '../../shared';

export const petRoute: Routes = [
    {
        path: 'pet',
        component: PetComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'shelterApp.pet.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'pet/:id',
        component: PetDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'shelterApp.pet.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const petPopupRoute: Routes = [
    {
        path: 'pet-new',
        component: PetPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'shelterApp.pet.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'pet/:id/edit',
        component: PetPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'shelterApp.pet.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'pet/:id/delete',
        component: PetDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'shelterApp.pet.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
