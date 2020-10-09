import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { OrdersManagementTestModule } from '../../../test.module';
import { ForwarderUpdateComponent } from 'app/entities/forwarder/forwarder-update.component';
import { ForwarderService } from 'app/entities/forwarder/forwarder.service';
import { Forwarder } from 'app/shared/model/forwarder.model';

describe('Component Tests', () => {
  describe('Forwarder Management Update Component', () => {
    let comp: ForwarderUpdateComponent;
    let fixture: ComponentFixture<ForwarderUpdateComponent>;
    let service: ForwarderService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OrdersManagementTestModule],
        declarations: [ForwarderUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ForwarderUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ForwarderUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ForwarderService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Forwarder(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Forwarder();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
